const DATACY = {
  COMPONENTS: {
    MENUBAR: {
      PARENT: "Menu",
      SP: { CY: "Menu-MenuNavButton-Startseite", TXT: "Startseite" },
      MB: { CY: "Menu-MenuNavButton-Postkorb", TXT: "Postkorb" },
      SR: { CY: "Menu-MenuNavButton-Übersicht", TXT: "Übersicht" },
      AR: { CY: "Menu-MenuNavButton-Archiv", TXT: "Archiv" },
      NQ: { CY: "Menu-MenuButton-NewQuestion", TXT: "Neue Frage" },
      ADM: { CY: "Menu-MenuNavIconButton-Administration", TXT: "" },
      LV: { CY: "Menu-MenuNavButton-LiveView", TXT: "Live-Ansicht" },
      PS: { CY: "Menu-MenuNavButton-PrintSort", TXT: "Drucksortierung" },
      RD: { CY: "Menu-MenuButton-Redaction", TXT: "Redakteur" },
      NWD: { CY: "Menu-MenuButton-NewSpeech", TXT: "Neue Wortmeldung" },
      STG: { CY: "Menu-MenuNavButton-StageDashboard", TXT: "Bühne" },
      QA: { CY: "Menu-MenuNavButton-StageQa", TXT: "Fragen & Antworten" },
      LNG: {
        CY: "Menu-MenuIconPopup-Language",
        TXT: "",
        ITEM: "Menu-MenuPopupItem-Language"
      },
      USER: { CY: "Menu-MenuIconPopup-User", TXT: "", ITEM: "Menu-MenuPopupItem-User" },
      TEST: { CY: "Menu-MenuNavButton" }
    }
  },
  DRAWER: {
    MYTASKS: "App-NavigationDrawer-AppMenuList-AppMenuItem-MyTasks",
    INSTANCES: "App-NavigationDrawer-AppMenuList-AppMenuItem-Instances",
    PROCESSES: "App-NavigationDrawer-AppMenuList-AppMenuItem-Processes",
    OPEN: "App-NavigationDrawer-AppMenuList-AppMenuItem-Open",
    ASSIGNEDGROUPTASKS: "App-NavigationDrawer-AppMenuList-AppMenuItem-AssignedGroupTasks"
  },
  VIEWS: {
    PROCESSES: {

    },
    TASKS: {
      SHOWTEMPLATE: "showTemplate",
      LIST: {
        ROOT: "taskList"
      },
      PAGINATION: {}
    },
  },
  KEYCLOAK: {
    USERN: "#username",
    PASSW: "#password",
    SUBMIT: "#kc-login"
  },
};

export const API = {
  BACKEND_SERVICE: {
    TASKS: "/api/digitalwf-backend-service/rest/task/group/open*",
    FILTER: "/api/digitalwf-backend-service/rest/filter"
  }
}
export const DATACY_DRAWER = DATACY.DRAWER;
export const DATACY_PROCESSES = DATACY.VIEWS.PROCESSES;
export const DATACY_TASKS = DATACY.VIEWS.TASKS;
export const DATACY_KEYCLOAK = DATACY.KEYCLOAK;

