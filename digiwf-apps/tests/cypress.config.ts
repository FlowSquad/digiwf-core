import { defineConfig } from "cypress";
import * as dotenv from "dotenv";

dotenv.config();

// noinspection JSUnusedGlobalSymbols
export default defineConfig({
  // projectId: "9c4me5",
  port: 8101,
  viewportWidth: 1400,
  viewportHeight: 1200,
  reporter: "mochawesome",
  reporterOptions: {
    consoleReporter: "spec",
    quiet: false,
    reportDir: "tests/results",
    overwrite: false,
    html: false,
    json: true,
  },
  retries: {
    openMode: 0,
    runMode: 0,
  },
  videoUploadOnPasses: false,
  defaultCommandTimeout: 10000,
  downloadsFolder: "tests/downloads",
  fixturesFolder: "tests/fixtures",
  videosFolder: "tests/videos",
  screenshotsFolder: "tests/screenshots",
  trashAssetsBeforeRuns: true,
  // chromeWebSecurity: false,
  // scrollBehavior: false,
  component: {
    devServer: {
      framework: "vue",
      bundler: "vite",
      // optionally pass in webpack config
      // webpackConfig: require('./webpack.config')
    },
    specPattern: "tests/e2e/**/*.spec.{js,jsx,ts,tsx}",
    supportFile: false
  },
  // eslint-disable-next-line object-shorthand
  e2e: {
    baseUrl: "http://localhost:8082",
    numTestsKeptInMemory: 5,
    specPattern: "tests/e2e/**/*.spec.{js,jsx,ts,tsx}",
    supportFile: "tests/support/e2e.ts",
    // chromeWebSecurity: false,
    // experimentalSkipDomainInjection: ["*keycloak:8080", "*localhost:8082"],
    //
    setupNodeEvents(on, config) {
      const CONFIG = config;
      CONFIG.env.KEYCLOAK_ADMIN = process.env.KEYCLOAK_ADMIN;

      on("before:browser:launch", (browser, launchOptions) => {
        switch (browser.family) {
          case "chromium":
            // launchOptions.args.push("--force-device-scale-factor=1")
            launchOptions.args.push("--window-size=1800,1200");
            launchOptions.args.push("--auto-open-devtools-for-tabs");
            // launchOptions.preferences.default["download"] = {
            // default_directory: downloadDirectory}
            // we could also restrict the extension to only load when browser.isHeaded
            // is true const extensionFolder = path.resolve(__dirname, '..', '..',
            // '4.2.1_0') console.log('adding React DevTools extension from',
            // extensionFolder) launchOptions.args.push(extensionFolder)
            break;
          case "firefox":
            // launchOptions.args.push("--force-device-scale-factor=1")
            launchOptions.args.push("-devtools");
            launchOptions.args.push("--window-size=1800,1200");
            // launchOptions.preferences["pref.downloads.disable_button.edit_actions"]
            // = false
            // launchOptions.preferences["pdfjs.enabledCache.state"] = false
            // This is a workaround
            // for a cypress bug, which does not set the firefox download dir correctly.
            // launchOptions.preferences["browser.download.dir"] =
            // downloadDirWorkaround
            break;
          default:
        }
        return launchOptions;
      });
      on("task", {
        // getClipboard() {
        //     return new Promise((resolve) => {
        //         const clipboardText = clipboardyLib.readSync()
        //         if (clipboardText) {
        //             return resolve(clipboardText)
        //         } else {
        //             return null
        //         }
        //     })
        // },
        log(msg: string) {
          // eslint-disable-next-line no-console
          console.log(msg);
          return null;
        }
      });
      return CONFIG;
    }
  },
});
