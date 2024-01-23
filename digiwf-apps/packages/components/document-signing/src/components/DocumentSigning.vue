<template>
  <div>
    <v-card>
      <v-card-text>
        <h3>Status</h3>
        <p>
          Dokument: {{ filePath }}
          <v-chip v-if="isDocumentSigned" color="green">Erfolgreich unterschrieben</v-chip>
          <v-chip v-else color="red">Wartet auf Unterschrift</v-chip>
        </p>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" @click="openSignDocumentDialog()" v-if="!isDocumentSigned">Dokument Unterschreiben</v-btn>
      </v-card-actions>
    </v-card>

    <!-- Dialog -->
    <v-row justify="center" v-if="!isDocumentSigned">
      <v-dialog
        v-model="isDocumentSignDialogOpen"
        fullscreen
        hide-overlay
        transition="dialog-bottom-transition"
      >
        <v-card>
          <v-toolbar
            dark
            color="primary"
          >
            <v-btn
              icon
              dark
              @click="isDocumentSignDialogOpen = false"
            >
              <v-icon>mdi-close</v-icon>
            </v-btn>
            <v-toolbar-title>Dokument {{ filePath }} unterschreiben</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                dark
                text
                @click="updatePresignedUrl()" v-show="undUpdatedFileLocation"
              >
                Abschließen
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-card-text>
            <div style="height: 90vh">
              <iframe ref="doxiview" title="Dokument unterschreiben" width="100%" height="100%" style="border: none"></iframe>
            </div>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<script lang="ts">

import globalAxios from "axios";
import { computed, defineComponent, inject, onMounted, ref } from "vue";
import { FormContext } from "../../types";
import {getFilenames, getPresignedUrlForGet, getPresignedUrlForPut} from "@/middleware/presignedUrls";
import { getSigningUrl } from "@/apiClient/signingServiceCalls";

export default defineComponent({
  props: [
    'valid',
    'readonly',
    'hasFocused',
    'value',
    'options',
    'schema',
    'fullKey',
    'dense',
    'label',
    'disabled',
    'rules',
    'on'
  ],
  setup(props) {
    const doxiview = ref(null);
    const doxiviewMaster = new window.CibGetMasterController().createMaster()

    let downloadFilePresignedUrl = ref<string>();
    let updateFilePresignedUrl = ref<string>();
    let fileName = ref<string>();
    let signingUrl = ref<string>();
    let signingHost = ref<string>();
    let undFileLocation = ref<string>();
    let undUpdatedFileLocation = ref<string>();
    let isDocumentSigned = ref<boolean>(false);
    let isDocumentSignDialogOpen = ref<boolean>(true);

    const filePath = computed(() => {
      return props.schema.filePath ? props.schema.filePath : '';
    })

    const apiEndpoint = inject<string>('apiEndpoint');
    const taskServiceApiEndpoint = inject<string>('taskServiceApiEndpoint');
    const integrationServicesApiEndpoint = inject<string>('integrationServicesApiEndpoint');
    const formContext = inject<FormContext>('formContext');

    const init = async () => {
      const filesInFolder = await getFilenames( {
        filePath,
        apiEndpoint: apiEndpoint || "",
        formContext,
        taskServiceApiEndpoint: taskServiceApiEndpoint || ""
      });
      fileName.value = filesInFolder[0];

      // presginedUrls
      downloadFilePresignedUrl.value = await getPresignedUrlForGet(fileName.value, {
        filePath,
        apiEndpoint: apiEndpoint || "",
        formContext,
        taskServiceApiEndpoint: taskServiceApiEndpoint || ""
      });

      updateFilePresignedUrl.value = await getPresignedUrlForPut(fileName.value, {
        filePath,
        apiEndpoint: apiEndpoint || "",
        formContext,
        taskServiceApiEndpoint: taskServiceApiEndpoint || ""
      });

      // signingUrl
      const sgn = await getSigningUrl(integrationServicesApiEndpoint || "");
      signingUrl.value = sgn.signingUrl;
      signingHost.value = sgn.signingHost;

      // debugging
      console.log('signingUrl', signingUrl.value);
      console.log('downloadFilePresignedUrl', downloadFilePresignedUrl.value);
      console.log('updateFilePresignedUrl', updateFilePresignedUrl.value);
      initDoxiviewMaster();
      openDoxiview();
    }

    const initDoxiviewMaster = () => {
      doxiviewMaster.registerFunction("getStartParameters", getStartParameters, false)
      doxiviewMaster.registerFunction("terminate", onTerminate, false)
      doxiviewMaster.registerFunction("onDocumentVersionUpdated", onDocumentVersionUpdated, false)
      console.log('doxiview IWC initialized');
    }

    const openDoxiview = async () => {
      console.log('open doxiview iFrame');
      // download from s3
      globalAxios.get(downloadFilePresignedUrl.value!, {
        responseType: "arraybuffer",
      }).then( res => {
        // upload to UnD service
        const formData = new FormData()
        formData.append('file', new Blob([res.data], { type: 'application/pdf' }));
        return globalAxios.post(signingHost.value + '/und-service/file', formData)})
      .then( res => {
        // build UnD download URL and open the doxiview iframe
        undFileLocation.value = signingHost.value + '/und-service/file/' + res.data
        doxiviewMaster.openURLInFrame(doxiview.value, signingUrl.value)
      });
    }

    const getStartParameters = () => {
      console.log('doxiview getStartParameters')
      return {
        doxiview: {
          urlField: undFileLocation.value,
          docName: fileName.value,
          ext: 'pdf',
          repo_alias: 'http-repository',
          useUniqueCache: true,
          formEnabled: true,
          checkSignatureInfoEnabled: false,
          optionsbarVisible: true,
          enableAllFunctions: true,
          optionsbarContent: 'form_creation',
          defaultFormFieldType: 'Signature',
          formCreationSignatureDefaultHeight: 24,
          formCreationSignatureDefaultWidth: 84,
          documentVersionUpdateMode: 'UND',
          applyDocumentVersionUpdate: true
        }
      }
    }

    const onTerminate = () => {
      console.log('doxiview terminate')
      isDocumentSignDialogOpen.value = false;
      // TODO remove close option in doxiview
      // reload doxiview
      openDoxiview();
    }

    const onDocumentVersionUpdated = (evt: { location: string; }) => {
      console.log("doxiview onDocumentVersionUpdated")
      // TODO adjust doxiview config on https://lhm-digital4finance.dev.cib.de/ to get the correct ingress URL
      let id = evt.location.substring(evt.location.lastIndexOf('/') + 1);
      undUpdatedFileLocation.value = signingHost.value + '/und-service/file/' + id;
    }

    const openSignDocumentDialog = () => {
      isDocumentSignDialogOpen.value = true;
    }

    const updatePresignedUrl = async () => {
      // download from UnD service
      globalAxios.get(undUpdatedFileLocation.value!, {
        responseType: "arraybuffer",
      }).then( res => {
        // update s3
        return globalAxios.put(updateFilePresignedUrl.value!, res.data);
      }).then( () => {
        // closes the iFrame
        isDocumentSigned.value = true;
        isDocumentSignDialogOpen.value = false
      });
    }

    onMounted(() => {
      init();
    })

    return {
      filePath,
      isDocumentSigned,
      isDocumentSignDialogOpen,
      openSignDocumentDialog,
      updatePresignedUrl,
      undUpdatedFileLocation,
      doxiview,
      doxiviewMaster
    };
  }

})

</script>

<style scoped>

</style>
