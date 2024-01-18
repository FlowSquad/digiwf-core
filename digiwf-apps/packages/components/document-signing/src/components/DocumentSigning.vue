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
                @click="signDocument()"
              >
                Fertig
              </v-btn>
            </v-toolbar-items>
          </v-toolbar>
          <v-card-text>
            <div style="height: 90vh">
              <!-- Todo use signingUrl -->
<!--              <iframe title="Dokument unterschreiben" width="100%" height="100%" :src="getIframeUrl()"></iframe>-->
            </div>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<script lang="ts">

import { computed, defineComponent, inject, onMounted, ref } from "vue";
import { FormContext } from "../../types";
import { getFilenames, getPresignedUrlForGet } from "@/middleware/presignedUrls";
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
    let downloadFilePresignedUrl = ref<string>();
    let updateFilePresignedUrl = ref<string>();
    let signingUrl = ref<string>();
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
      const fileName = filesInFolder[0];

      // presginedUrls
      const getPresignedUrl = await getPresignedUrlForGet(fileName, {
        filePath,
        apiEndpoint: apiEndpoint || "",
        formContext,
        taskServiceApiEndpoint: taskServiceApiEndpoint || ""
      });
      downloadFilePresignedUrl.value = getPresignedUrl;

      const putPresignedUrl = await getPresignedUrlForGet(fileName, {
        filePath,
        apiEndpoint: apiEndpoint || "",
        formContext,
        taskServiceApiEndpoint: taskServiceApiEndpoint || ""
      });
      updateFilePresignedUrl.value = putPresignedUrl;

      // signingUrl
      const sgn = await getSigningUrl(integrationServicesApiEndpoint || "");
      signingUrl.value = sgn.signingUrl;

      // debugging
      console.log('signingUrl', signingUrl.value);
      console.log('downloadFilePresignedUrl', downloadFilePresignedUrl.value);
      console.log('updateFilePresignedUrl', updateFilePresignedUrl.value);
    }

    const openSignDocumentDialog = () => {
      isDocumentSignDialogOpen.value = true;
    }

    const signDocument = () => {
      isDocumentSigned.value = true;
      isDocumentSignDialogOpen.value = false;
    }

    const getIframeUrl = () => {
      return "https://doxiview.com/showcase/?locale=de#de&feature=sign";
    }

    onMounted(() => {
      init();
    })

    return {
      filePath,
      isDocumentSigned,
      isDocumentSignDialogOpen,
      openSignDocumentDialog,
      getIframeUrl,
      signDocument
    };
  }

})

</script>

<style scoped>

</style>
