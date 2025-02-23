<template>
  <v-app>
    <v-app-bar
      app
      clipped-left
      dark
      color="secondary"
    >
      <v-app-bar-nav-icon
        aria-hidden="false"
        aria-label="Menü öffnen/schließen"
        @click.stop="drawer = !drawer"
      />

      <router-link
        style="text-decoration: none;"
        to="/"
      >
        <v-toolbar-title class="font-weight-bold">
          <span class="white--text">Digi</span>
          <span :style="{color: stage?.color}">WF</span>
        </v-toolbar-title>
      </router-link>
      <v-spacer/>
      <span>{{ stage?.displayName }}</span>
      <v-spacer/>
      <app-help-menu
        @openKeyBindingsDialoge="openKeyBindingsDialoge"
        @closeKeyBindingsDialoge="closeKeyBindingsDialoge"
      />

      {{ username }}

    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      app
      clipped
      width="300"
    >
      <AppMenuList :number-of-process-instances="processInstancesCount"/>
    </v-navigation-drawer>
    <v-main class="main">
      <v-banner
        v-if="appInfo && appInfo.maintenanceInfo1"
        class="maintenance"
        multi-line
        transition="slide-y-transition"
        color="orange darken-1"
        elevation="1"
        icon="mdi-alert-circle-outline"
        icon-color="black"
      >
        <p class="body-1 my-1">
          {{ appInfo.maintenanceInfo1 }}
        </p>
        <p class="body-2 my-1">
          {{ appInfo.maintenanceInfo2 }}
        </p>
      </v-banner>
      <v-banner
        :value="!loggedIn"
        icon="mdi-alert"
        color="error"
        single-line
        sticky
      >
        <template v-if="loginLoading">
          Sie werden angemeldet...
        </template>
        <template v-else>
          Sie sind aktuell nicht (mehr) angemeldet!
        </template>
        <template #actions>
          <v-btn
            text
            :loading="loginLoading"
            @click="login"
          >
            Login
          </v-btn>
        </template>
      </v-banner>
      <app-key-bindings-dialog
        :value="showKeyBindingsModal"
        @close="closeKeyBindingsDialoge"
      />
      <v-container fluid>
        <v-fade-transition mode="out-in">
          <router-view/>
        </v-fade-transition>
      </v-container>
    </v-main>
  </v-app>
</template>

<style scoped>
.maintenance >>> .v-banner__wrapper {
  padding: 0;
}

.maintenance >>> .v-avatar {
  margin: 8px;
}

</style>

<style>

.hrDividerMenu {
  border: 0;
  border-top: 1px solid #ddd;
  margin: -2px 20px 0 20px;
}


.hrDivider {
  border: 0;
  border-top: 1px solid #ddd;
  margin: 0 5px;
}

.main {
  background-color: white;
}

html {
  overflow: auto;
}

button {
  text-transform: none !important;
}

a {
  text-transform: none !important;
}

/* Set table style for markdown tables */
.vjsf-markdown-input table {
  border-collapse: collapse;
  margin: 25px 0;
  font-size: 0.9em;
  font-family: sans-serif;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
}

.vjsf-markdown-input thead tr {
  background-color: var(--v-secondary-base);
  color: white;
  text-align: left;
}

.vjsf-markdown-input th,
.vjsf-markdown-input td {
  padding: 12px 15px;
}

.vjsf-markdown-input tbody tr {
  border-bottom: 1px solid white;
}

.vjsf-markdown-input tbody tr:nth-of-type(even) {
  background-color: lightgray;
}
</style>


<script lang="ts">
import Vue from "vue";
import {Component, Watch} from "vue-property-decorator";
import {InfoTO, ServiceInstanceTO, UserTO,} from "@muenchen/digiwf-engine-api-internal";
import AppMenuList from "./components/UI/appMenu/AppMenuList.vue";
import AppHelpMenu from "./components/UI/help/AppHelpMenu.vue";
import {apiGatewayUrl} from "./utils/envVariables";
import {queryClient} from "./middleware/queryClient";
import StageInfoService, {StageInfo} from "./api/StageInfoService";

@Component({
  components: {AppHelpMenu, AppMenuList}
})
export default class App extends Vue {
  drawer = true;
  processInstancesCount: number | null = null;
  username = "";
  appInfo: InfoTO | null = null;
  loginLoading = false;
  loggedIn = true;
  showKeyBindingsModal = false;
  stage: StageInfo = StageInfoService.getDefaultStageInfo();

  created(): void {
    this.loadData();
  }

  loadData(refresh = false): void {
    StageInfoService.getStageInfo().then((stageInfo) => {
      this.stage = stageInfo;
    });
    this.$store.dispatch("user/getUserInfo", refresh);
    this.$store.dispatch("info/getInfo", refresh);
    this.drawer = this.$store.getters["menu/open"];
  }

  getUser(): void {
    this.loginLoading = true;
    this.$store.dispatch("user/getUserInfo", true);
    this.loginLoading = false;
  }

  @Watch("$store.state.menu.open")
  onMenuChanged(menuOpen: boolean): void {
    this.drawer = menuOpen;
  }

  @Watch("$store.state.user.info")
  setUserName(user: UserTO): void {
    this.username = user.forename && user.surname ? user.forename + " " + user.surname : "";
    // if session is not valid, user is updated to an empty object in redux store
    this.loggedIn = !!user.username;
  }

  @Watch("$store.state.processInstances.processInstances")
  setMyProcessInstancesCount(processInstances: ServiceInstanceTO[]): void {
    this.processInstancesCount = processInstances.length;
  }

  @Watch("$store.state.info.info")
  setAppInfo(info: InfoTO): void {
    this.appInfo = info;
  }

  login(): void {
    let popup = window.open(`${apiGatewayUrl}/loginsuccess.html`);

    popup?.focus();
    let timer = setInterval(() => {
      if (popup?.closed ?? true) {
        clearInterval(timer);
        this.getUser();
        queryClient.refetchQueries();
      }
    }, 1000);
  }

  openKeyBindingsDialoge(): void {
    this.showKeyBindingsModal = true;
  }

  closeKeyBindingsDialoge(): void {
    this.showKeyBindingsModal = false;
  }
}
</script>
