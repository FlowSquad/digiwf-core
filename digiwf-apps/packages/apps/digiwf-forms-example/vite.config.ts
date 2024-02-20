import vue from "@vitejs/plugin-vue2";
import {defineConfig} from 'vite'
import Components from 'unplugin-vue-components/vite'
import {VuetifyResolver} from "unplugin-vue-components/resolvers";
//@ts-ignore
import {fileURLToPath, URL} from "url";

export default defineConfig({
  plugins: [
    vue(/* options */),
    Components({
      transformer: 'vue2',
      dts: true,
      resolvers: [
        VuetifyResolver()
      ]
    })
  ],
  server: {
    port: 8085
  },
  build: {
    commonjsOptions: {
      transformMixedEsModules: true,
    },
    minify: 'esbuild'
  },
  resolve: {
    alias: {
      //@ts-ignore
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  }
})
