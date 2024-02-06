<template>
  <div class="pa-0">
    <iframe ref="webform" :src="webformUrl" height="600px" width="100%" style="border: none"></iframe>
    <v-btn color="secondary" @click="submit()" style="margin: 15px">
      Übernehmen
    </v-btn>
    <v-chip v-if="!webformDataSuccess" color="orange">Wartet auf Daten</v-chip>
  </div>
</template>

<script lang="ts">

import {defineComponent, onMounted, ref, watch} from "vue";

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
    const webform = ref(null);
    const webformUrl = props.value;

    let webformDataSuccess = ref<boolean>();
    let webformData = ref<object>();

    const init = () => {
      createWebformListener();
    }

    onMounted(() => {
      init();
    })

    watch(webformData, (data: any) => {
      props.on.input(data);
      webformDataSuccess.value = true;
    })

    const createWebformListener = () => {
      window.addEventListener('message', function (evt) { // post message handler that reacts to webform events
        if (evt.data.type === 'submit') {
          console.log('webform submit')
          console.log(evt.data) // submit response: data & field settings
          webformData.value = evt.data.data
        } else if (evt.data.type === 'ready') { // webform has finished loading
          console.log('webform ready')
          webform.value!.contentWindow.postMessage({type: 'init', data: {}, fieldSettings: {}, defaults: {}}, '*')
        } else if (evt.data.type === 'validation') {
          console.log('webform validation')
          let data = evt.data
          console.log('Constraint ' + data.fieldSetting + ' on field ' + data.field + ' is violated. Actual constraint:')
          console.log(data.fieldSettingValue)
        } else if (evt.data.type === 'exception') {
          console.log('webform exception')
          console.log('Error in event ' + evt.data.event + ' with exception ' + evt.data.exception)
          console.log(evt.data.params)
        } else if (evt.data.type === 'alert') {
          console.log('webform alert')
          console.log('Alert: ' + evt.data.message)
        }
      })
    }

    const submit = () => {
      webform.value!.contentWindow.postMessage({type: 'submit'}, '*')
    }

    return {
      webform,
      webformUrl,
      webformData,
      webformDataSuccess,
      submit,
    };
  }
})

</script>

<style scoped>

</style>
