<template>
  <div class="pa-0">
    <div style="margin-bottom: 30px">
      <v-row>
        <v-col cols="12" v-if="fileName">
          <p>
            Dokument: {{ fileName }}
            <v-chip v-if="isDocumentSigned" color="green">Erfolgreich unterschrieben</v-chip>
            <v-chip v-else color="orange">Wartet auf Unterschrift</v-chip>
          </p>
          <!-- Dokument unterschreiben -->
          <v-tooltip bottom v-if="!isDocumentSigned">
            <template v-slot:activator="{ on }">
              <v-btn v-on="on" color="secondary" @click="openSignDocumentDialog()" :style="buttonStyles">
                <v-icon>mdi-pencil</v-icon>
              </v-btn>
            </template>
            Dokument unterschreiben
          </v-tooltip>
          <!-- Unterschriebenes Dokument ansehen -->
          <v-tooltip bottom v-if="isDocumentSigned">
            <template v-slot:activator="{ on }">
              <v-btn v-on="on" color="secondary" @click="openSignDocumentDialog()" :style="buttonStyles">
                <v-icon>mdi-eye</v-icon>
              </v-btn>
            </template>
            Unterschriebenes Dokument ansehen
          </v-tooltip>
          <!-- Unterschriebenes Dokument herunterladen -->
          <v-tooltip bottom v-if="isDocumentSigned">
            <template v-slot:activator="{ on }">
              <v-btn v-on="on" @click="downloadSignedFile()" :style="buttonStyles">
                <v-icon>mdi-download</v-icon>
              </v-btn>
            </template>
            Unterschriebenes Dokument herunterladen
          </v-tooltip>
        </v-col>
        <v-col cols="12" v-else>
          <p>Bitte laden Sie ein Dokument hoch.</p>
        </v-col>
      </v-row>
    </div>

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
            <v-toolbar-title>Signaturfeld aufbringen oder Dokument digital signieren</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
              <v-btn
                dark
                text
                @click="signDocument()" :disabled="!undUpdatedFileLocation"
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
import { getFilenames, getPresignedUrlForGet, getPresignedUrlForPut } from "@/middleware/presignedUrls";
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
    // can't be false -> iframe doesn't load
    let isDocumentSignDialogOpen = ref<boolean>(true);

    const filePath = computed(() => {
      return props.schema.filePath ? props.schema.filePath : '';
    })

    const apiEndpoint = inject<string>('apiEndpoint');
    const taskServiceApiEndpoint = inject<string>('taskServiceApiEndpoint');
    const integrationServicesApiEndpoint = inject<string>('integrationServicesApiEndpoint');
    const formContext = inject<FormContext>('formContext');

    const buttonStyles = {
      margin: "15px"
    }

    const init = async () => {
      const filesInFolder = await getFilenames( {
        filePath,
        apiEndpoint: apiEndpoint || "",
        formContext,
        taskServiceApiEndpoint: taskServiceApiEndpoint || ""
      });

      console.log('filesInFolder', filesInFolder);
      if (filesInFolder.length > 0) {
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
        initDoxiviewMaster();
        openDoxiview();
      }
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
        console.log('open doxiview iFrame success');
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

    const signDocument = async () => {
      const res = await globalAxios.get(undUpdatedFileLocation.value!, {
        responseType: "arraybuffer",
      });
      // update s3
      await globalAxios.put(updateFilePresignedUrl.value!, res.data);
      // closes the iFrame
      isDocumentSigned.value = true;
      isDocumentSignDialogOpen.value = false;
    }

    const downloadSignedFile = async () => {
      const downloadFile = await getPresignedUrlForGet(fileName.value, {
        filePath,
        apiEndpoint: apiEndpoint || "",
        formContext,
        taskServiceApiEndpoint: taskServiceApiEndpoint || ""
      });
      window.open(downloadFile, '_blank');
    }

    onMounted(() => {
      init();
    })

    return {
      filePath,
      fileName,
      isDocumentSigned,
      isDocumentSignDialogOpen,
      doxiview,
      doxiviewMaster,
      undUpdatedFileLocation,
      openSignDocumentDialog,
      signDocument,
      downloadSignedFile,
      buttonStyles
    };
  }

})

</script>

<style scoped>

</style>
