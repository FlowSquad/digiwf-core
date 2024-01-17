<template>
  <div>
    <h1>Document Signing</h1>
  </div>
</template>

<script lang="ts">

import { defineComponent } from "vue";

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

    const filePath = props.schema.filePath;
    console.log(filePath);

  }

})

</script>

<style scoped>

</style>
