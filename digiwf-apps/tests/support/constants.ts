const DATACY = {
  COMPONENTS: {
    MENU_BAR: {
      //
    }
  },
  DRAWER: {
    MY_TASKS: "App-NavigationDrawer-AppMenuList-AppMenuItem-MyTasks",
    INSTANCES: "App-NavigationDrawer-AppMenuList-AppMenuItem-Instances",
    PROCESSES: "App-NavigationDrawer-AppMenuList-AppMenuItem-Processes",
    OPEN: "App-NavigationDrawer-AppMenuList-AppMenuItem-Open",
    ASSIGNED_GROUP_TASKS: "App-NavigationDrawer-AppMenuList-AppMenuItem-AssignedGroupTasks"
  },
  VIEWS: {
    INSTANCES: {
      SEARCH_FIELD: "ProcessInstances-SearchField",
      LIST: {
        ROOT: "ProcessInstances-CurrentList",
        ITEM: "ProcessInstances-Instance-",
      }
    },
    PROCESSES: {
      HEADLINE: "StartProcess-Headline",
      SEARCH_FIELD: "Processes-SearchField",
      ITEM: "Processes-ProcessDefinitionItem",
      BEGIN_CONTRACT: "AntragDVMoHo",
      CHANGE_CONTRACT: "AenderungsAntragDVMoHo",
      EMPTY_CONTRACT: "DigitalWFErleben"
    },
    TASKS: {
      SHOWTEMPLATE: "showTemplate",
      LIST: {
        ROOT: "Tasks-List"
      },
      PAGINATION: {}
    },
  }
};

export const KEYCLOAK = {
  USERN: "#username",
  PASSW: "#password",
  SUBMIT: "#kc-login"
}
export const API = {
  BACKEND_SERVICE: {
    TASKS: "/api/digitalwf-backend-service/rest/task/group/open*",
    FILTER: "/api/digitalwf-backend-service/rest/filter"
  }
}
export const MSG = [
  "Keine laufenden Vorgänge gefunden"
]
export const DATACY_DRAWER = DATACY.DRAWER;
export const DATACY_INSTANCES = DATACY.VIEWS.INSTANCES;
export const DATACY_PROCESSES = DATACY.VIEWS.PROCESSES;
export const DATACY_TASKS = DATACY.VIEWS.TASKS;
