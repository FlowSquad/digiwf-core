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
      const getPresignedUrl = await getPresignedUrlForGet(fileName.value, {
        filePath,
        apiEndpoint: apiEndpoint || "",
        formContext,
        taskServiceApiEndpoint: taskServiceApiEndpoint || ""
      });
      downloadFilePresignedUrl.value = getPresignedUrl;

      const putPresignedUrl = await getPresignedUrlForPut(fileName.value, {
        filePath,
        apiEndpoint: apiEndpoint || "",
        formContext,
        taskServiceApiEndpoint: taskServiceApiEndpoint || ""
      });
      updateFilePresignedUrl.value = putPresignedUrl;

      // signingUrl
      const sgn = await getSigningUrl(integrationServicesApiEndpoint || "");
      signingUrl.value = sgn.signingUrl;
      signingHost.value = sgn.signingHost;

      // debugging
      console.log('signingUrl', signingUrl.value);
      console.log('downloadFilePresignedUrl', downloadFilePresignedUrl.value);
      console.log('updateFilePresignedUrl', updateFilePresignedUrl.value);
      initDoxiviewMaster();
    }

    const initDoxiviewMaster = () => {
      doxiviewMaster.registerFunction("getStartParameters", getStartParameters, false)
      doxiviewMaster.registerFunction("terminate", onTerminate, false)
      doxiviewMaster.registerFunction("onDocumentVersionUpdated", onDocumentVersionUpdated, false)
      doxiviewMaster.registerFunction("onSignatureFieldSigned", onSignatureFieldSigned, false)
      doxiviewMaster.openURLInFrame(doxiview.value, signingUrl.value)
      console.log('doxiview master initialized');
    }

    var getStartParameters = () => {
      console.log('doxiview getStartParameters')
      return {
        doxiview: {
          urlField: downloadFilePresignedUrl.value,
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
/*          documentVersionUpdateMode: 'UND',
          applyDocumentVersionUpdate: true*/
        }
      }
    }

    const onTerminate = () => {
      console.log('doxiview terminate')
      isDocumentSignDialogOpen.value = false;
    }

    const onDocumentVersionUpdated = () => {
      console.log("doxiview onDocumentVersionUpdated")
    }

    const onSignatureFieldSigned = (evt: { processId: any; }) => {
      console.log("doxiview onSignatureFieldSigned")
      const downloadUrl = signingHost.value + '/doxisign/rest/process/' + evt.processId + '/document?fileName=' + fileName.value
      downloadSignedDocument(downloadUrl);
    }

    const openSignDocumentDialog = () => {
      isDocumentSignDialogOpen.value = true;
    }

    const downloadSignedDocument = async (downloadUrl: string) => {
      const res = await globalAxios.get(downloadUrl, {
        responseType: "arraybuffer",
      });
      // update s3
      if (updateFilePresignedUrl.value)
        await globalAxios.put(updateFilePresignedUrl.value, res.data);

      // closes the iFrame
      isDocumentSigned.value = true;
      isDocumentSignDialogOpen.value = false;
    }

    onMounted(() => {
      init();
    })

    return {
      filePath,
      isDocumentSigned,
      isDocumentSignDialogOpen,
      openSignDocumentDialog,
      doxiview,
      doxiviewMaster
    };
  }

})

</script>

<style scoped>

</style>
