export const DRAWERS = {
  TASKS: {
    DRAWER: "App-NavigationDrawer-AppMenuList-AppMenuItem-MyTasks",
    HEADLINE: "Tasks-Headline",
    SEARCH_FIELD: "Tasks-SearchField",
    SHOW_TEMPLATE: "Tasks-ShowTemplate",
    LIST: {
      ROOT: "Tasks-List",
      ITEM_HEADLINE: "TaskDetail-Headline",
      ITEM: "Tasks-Item",
      MSG: "Keine Aufgaben gefunden"
    },
    PAGINATION: {}
  },
  INSTANCES: {
    DRAWER: "App-NavigationDrawer-AppMenuList-AppMenuItem-Instances",
    HEADLINE: "ProcessInstances-Headline",
    SEARCH_FIELD: "ProcessInstances-SearchField",
    LIST: {
      ROOT: "ProcessInstances-List",
      ITEM_HEADLINE: "ProcessInstanceDetailView-Headline",
      ITEM: "ProcessInstances-Item",
      MSG: "Keine laufenden Vorgänge gefunden"
    }
  },
  PROCESSES: {
    DRAWER: "App-NavigationDrawer-AppMenuList-AppMenuItem-Processes",
    HEADLINE: "Processes-Headline",
    SEARCH_FIELD: "Processes-SearchField",
    LIST: {
      ROOT: "Processes-List",
      ITEM_HEADLINE: "StartProcess-Headline",
      ITEM: "Processes-Item",
      MSG: "Keine Vorgänge gefunden"
    }
  },
  OPEN: {
    DRAWER: "App-NavigationDrawer-AppMenuList-AppMenuItem-Open"
  },
  ASSIGNED_GROUP_TASKS: {
    DRAWER: "App-NavigationDrawer-AppMenuList-AppMenuItem-AssignedGroupTasks"
  }
}

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

export const test_schema = `{
  "type": "object",
  "properties": {
    "name": {
      "type": "string",
      "minLength": 3,
      "description": "Please enter your name"
    },
    "vegetarian": {
      "type": "boolean"
    },
    "birthDate": {
      "type": "string",
      "format": "date"
    },
    "nationality": {
      "type": "string",
      "enum": [
        "DE",
        "IT",
        "JP",
        "US",
        "RU",
        "Other"
      ]
    },
    "personalData": {
      "type": "object",
      "properties": {
        "age": {
          "type": "integer",
          "description": "Please enter your age."
        },
        "height": {
          "type": "number"
        },
        "drivingSkill": {
          "type": "number",
          "maximum": 10,
          "minimum": 1,
          "default": 7
        }
      },
      "required": [
        "age",
        "height"
      ]
    },
    "occupation": {
      "type": "string"
    },
    "postalCode": {
      "type": "string",
      "maxLength": 5
    }
  },
  "required": [
    "occupation",
    "nationality"
  ]
}`

export const test_data = `{
  "name": "John Doe",
  "vegetarian": false,
  "birthDate": "1985-06-02",
  "personalData": {
    "age": 34
  },
  "postalCode": "12345"
}`

