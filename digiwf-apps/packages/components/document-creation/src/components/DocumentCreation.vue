<template>
  <div class="pa-0">
    <p>Document creation component works!</p>
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
    const formValue = ref<object>();

    const init = () => {
      console.log('Document creation component initialized!');
      formValue.value = {"foo": "bar"};
      createDocument();
    }

    onMounted(() => {
      init();
    })

    const createDocument = () => {
      props.on.input(JSON.stringify(formValue.value));
    }

    return {
      createDocument
    };
  }
})

</script>

<style scoped>

</style>
