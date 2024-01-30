<template>
  <div class="pa-0">
    <iframe src="https://cosys4lhm-dev.cib.de/webtom/webform/fa2858ff1dafd0b06b4c1f3436fa1450/html" ref="webform" width="100%" height="400px"></iframe>
    <v-btn color="secondary" @click="submit()" style="margin: 15px">
      Submit
    </v-btn>
    <span> {{ formValue }}</span>
  </div>
</template>

<script lang="ts">

import { defineComponent, onMounted, ref } from "vue";

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

    const formValue = ref<object>();

    const init = () => {
      console.log('Document creation component initialized!');
      createWebformListener();
    }

    onMounted(() => {
      init();
    })

    const createWebformListener = () => {
      window.addEventListener('message', function(evt) { // post message handler that reacts to webform events
        if (evt.data.type === 'submit') {
          console.log('webform submit')
          console.log(evt.data) // submit response: data & field settings
          formValue.value = evt.data.data
          createDocument()
        } else if (evt.data.type === 'ready') { // webform has finished loading
          console.log('webform ready')
          console.log(evt.data)
          webform.value!.contentWindow.postMessage({ type: 'init', data: {}, fieldSettings: {}, defaults: {} }, '*')
        } else if (evt.data.type === 'validation') {
          console.log('webform validation')
          let data = evt.data
          console.log(data)
          console.log('Constraint ' + data.fieldSetting + ' on field ' + data.field + ' is violated. Actual constraint:')
          console.log(data.fieldSettingValue)
        } else if (evt.data.type === 'exception') {
          console.log('webform exception')
          console.log(evt.data)
          console.log('Error in event ' + evt.data.event + ' with exception ' + evt.data.exception)
          console.log(evt.data.params)
        } else if (evt.data.type === 'alert') {
          console.log('webform alert')
          console.log(evt.data)
          console.log('Alert: ' + evt.data.message)
        }
      })
    }

    const submit = () => {
      webform.value!.contentWindow.postMessage({ type: 'submit' }, '*')
    }

    const createDocument = () => {
      props.on.input(JSON.stringify(formValue.value));
    }

    return {
      webform,
      submit,
      createDocument,
      formValue
    };
  }
})

</script>

<style scoped>

</style>
