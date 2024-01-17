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
              <iframe title="Dokument unterschreiben" width="100%" height="100%" :src="getIframeUrl()"></iframe>
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
    let presignedUrlToFile = ref<string>();
    let signingUrl = ref<string>();
    let isDocumentSigned = ref<boolean>(false);
    let isDocumentSignDialogOpen = ref<boolean>(true);

    const filePath = computed(() => {
      return props.schema.filePath ? props.schema.filePath : '';
    })

    const apiEndpoint = inject<string>('apiEndpoint');
    const taskServiceApiEndpoint = inject<string>('taskServiceApiEndpoint');
    const formContext = inject<FormContext>('formContext');

    const init = async () => {
      const presignedUrl = "http://127.0.0.1:9000/digiwf-bucket/test/Unbenannt.pdf?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=83MTG3HYDUH9HEVLCPDM%2F20240117%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240117T160511Z&X-Amz-Expires=43200&X-Amz-Security-Token=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NLZXkiOiI4M01URzNIWURVSDlIRVZMQ1BETSIsImV4cCI6MTcwNTU0OTU2NiwicGFyZW50IjoibWluaW8ifQ.buyYAnnm9tnEhCHWd0aGKpGpTteQWANWEtnxHY26KtuJ9ZkjFuVBUbzffROyGlKLP_ce4erUNFrm4eSyjP5qQA&X-Amz-SignedHeaders=host&versionId=null&X-Amz-Signature=8b8850057866292673751669a4b7e41d308be5ee4fdad43d06de8a19325b2c7e";
      // const presignedUrl = await getPresignedUrlForGet(filePath.value, {
      //   filePath,
      //   apiEndpoint: apiEndpoint || "",
      //   formContext,
      //   taskServiceApiEndpoint: taskServiceApiEndpoint || ""
      // });
      presignedUrlToFile.value = presignedUrl;

      const sgnUrl = "http://localhost:10000/doxiview";
      signingUrl.value = sgnUrl;
    }

    const openSignDocumentDialog = () => {
      isDocumentSignDialogOpen.value = true;
    }

    const signDocument = () => {
      isDocumentSigned.value = true;
      isDocumentSignDialogOpen.value = false;
    }

    const getIframeUrl = () => {
      // TODO do we need both urls
      // return `${signingUrl.value}?presignedUrl=${presignedUrlToFile.value}`;
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
