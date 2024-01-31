<template>
  <div class="pa-0">
    <iframe ref="webform" :src="getWebformUrl" height="400px" width="100%"></iframe>
    <v-btn color="secondary" style="margin: 15px" @click="submit()">
      Submit
    </v-btn>
    <span> {{ value }}</span>
    <span> {{ formValue }}</span>
  </div>
</template>

<script lang="ts">

import {defineComponent, onMounted, ref} from "vue";

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
    const formValue = ref<any>();

    const init = () => {
      console.log('Document creation component initialized!');
      createWebformListener();
    }

    const getWebformUrl = () => {
      try {
        const jsonValue = JSON.parse(props.value);
        return jsonValue.webformId;
      } catch (e) {
        return props.value
      }
    }

    onMounted(() => {
      init();
    })

    const createWebformListener = () => {
      window.addEventListener('message', function (evt) { // post message handler that reacts to webform events
        if (evt.data.type === 'submit') {
          console.log('webform submit')
          console.log(evt.data) // submit response: data & field settings
          formValue.value = evt.data.data
          createDocument()
        } else if (evt.data.type === 'ready') { // webform has finished loading
          console.log('webform ready')
          console.log(evt.data)
          webform.value!.contentWindow.postMessage({type: 'init', data: {}, fieldSettings: {}, defaults: {}}, '*')
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
      webform.value!.contentWindow.postMessage({type: 'submit'}, '*')

      props.on.input(JSON.stringify({
        webformId: getWebformUrl(),
        data: {
          "myField": "myValue",
          "myField2": "myValue2",
          "myField3": "myValue3"
        }
      }));
    }

    const createDocument = () => {
      props.on.input(JSON.stringify({
        webformId: getWebformUrl(),
        data: {
          "myField": "myValue",
          "myField2": "myValue2",
          "myField3": "myValue3"
        }
      }));
    }

    return {
      webform,
      submit,
      createDocument,
      formValue,
      getWebformUrl
    };
  }
})

</script>

<style scoped>

</style>
