const DATACY = {
  COMPONENTS: {
    CHAT: {
      CW: {
        PRIVATE: "ChatWidget-ChatGroupsView-ChatWidgetHeaderIconButton-PrivateChat",
        GROUP: "ChatWidget-ChatGroupsView-ChatWidgetHeaderIconButton-GroupChat",
        CLOSE: "ChatWidget-ChatGroupsView-ChatWidgetHeaderIconButton-Close",
        INPUT: "ChatWidget-ChatMessagesView-TextField-Text",
        GROUPS: "ChatWidget-ChatGroupsView",
        MESSAGES: "ChatWidget-ChatMessagesView-Messages"
      }
    },
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
    },
    PERMISSIONS: {
      FIND: "ROLE_SUCHE",
      INBOX: "ROLE_POSTKORB",
      ARCHIVE: "ROLE_ARCHIV",
      ABBREVIATION: "ROLE_ABKUERZUNGEN_VERWENDEN",
      //
      HISTORYVERSION: "ROLE_FRAGE_VERSIONSHISTORIE_ANZEIGEN",
      HISTORYSTATUS: "ROLE_FRAGE_STATUSHISTORIE_ANZEIGEN",
      HISTORYOTHER: "ROLE_FRAGE_SONSTIGE_HISTORIE_ANZEIGEN",
      CHANGESBACK: "ROLE_FRAGE_AENDERUNGEN_ZURUECKNEHMEN",
      //
      UNLOCK: "ROLE_FRAGE_SPERRE_AUFHEBEN",
      LOCKBYBUTTON: "ROLE_FRAGE_SPERREN_DURCH_BUTTON",
      LOCKBYKLICK: "ROLE_FRAGE_SPERREN_DURCH_KLICK",
      //
      NOTE: "ROLE_FRAGE_NOTIZ",
      DELETENOTE: "ROLE_FRAGE_NOTIZ_LOESCHEN",
      //
      CREATECOMMENT: "ROLE_FRAGE_KOMMENTAR_ERSTELLEN",
      DELETECOMMENT: "ROLE_FRAGE_KOMMENTAR_LOESCHEN",
      DELETEALLCOMMENT: "ROLE_FRAGE_ALLE_KOMMENTARE_LOESCHEN",
      //
      CREATE: "ROLE_FRAGE_ERSTELLEN",
      COPY: "ROLE_FRAGE_KOPIEREN",
      MERGE: "ROLE_FRAGE_ZUSAMMENFUEHREN",
      FORWARD: "ROLE_FRAGE_WEITERGEBEN",
      FORWARDONECLICK: "ROLE_FRAGE_WEITER_OHNE_ZWISCHENFENSTER",
      DELETE: "ROLE_FRAGE_LOESCHEN",
      RESTORE: "ROLE_FRAGE_WIEDERHERSTELLEN",
      ARCHIVEQUESTION: "ROLE_FRAGE_ARCHIVIEREN",
      DEARCHIVEQUESTION: "ROLE_FRAGE_ENTARCHIVIEREN",
      //
      PRINT: "ROLE_FRAGE_DRUCKEN",
      PRINTSORT: "ROLE_FRAGE_SORTIEREN",
      CATALOG: "ROLE_FRAGE_FUER_KATALOG_SORTIEREN",
      //
      TITLE: "ROLE_FRAGE_TITELTEXT_BEARBEITEN",
      QUESTIONTEXT: "ROLE_FRAGE_FRAGETEXT_BEARBEITEN",
      ANSWERTEXT: "ROLE_FRAGE_ANTWORTTEXT_BEARBEITEN",
      ATEXT: "ROLE_FRAGE_ZUSATZTEXT1_BEARBEITEN",
      BTEXT: "ROLE_FRAGE_ZUSATZTEXT2_BEARBEITEN",
      CTEXT: "ROLE_FRAGE_ZUSATZTEXT3_BEARBEITEN",
      //
      BATCH: "ROLE_STAPEL",
      KEYWORD: "ROLE_SCHLAGWORT_ERSTELLEN",
      REDAKTEUR: "ROLE_REDAKTEUR_QUERSPRUNG",
      //
      LIVE: "ROLE_LIVE_ANSICHT",
      STAGEQA: "ROLE_BUEHNE_QA",
      STAGE: "ROLE_BUEHNE",
      STAGEWM: "ROLE_BUEHNE_WORTMELDUNGEN",
      STAGECOMMENT: "ROLE_BUEHNE_KOMMENTARE_ANZEIGEN",
      STAGEQUERY: "ROLE_BUEHNE_FRAGE_BEARBEITEN",
      OWNQUESTIONS: "ROLE_EIGENE_FRAGEN_TAB_ANZEIGEN",
      SPEECHTAB: "ROLE_WORTMELDUNG_TAB_ANZEIGEN",
      //
      CHAT: "ROLE_CHAT",
      CHATSINGLE: "ROLE_CHAT_EINZEL_ERSTELLEN",
      CHATGROUP: "ROLE_CHAT_GRUPPE_ERSTELLEN",
      //
      CREATESPEAKER: "ROLE_REDNER_ERSTELLEN",
      EDITSPEAKER: "ROLE_REDNER_BEARBEITEN",
      DELETESPEAKER: "ROLE_REDNER_LOESCHEN",
      SPEECHEDIT: "ROLE_WORTMELDUNG_REDEZEIT_BEARBEITEN",
      SPEECHROUND: "ROLE_WORTMELDUNG_REDERUNDE_ERSTELLEN",
      SPEECHSTARTSTOP: "ROLE_WORTMELDUNG_REDEZEIT_STARTEN_STOPPEN"
    },
    STAGE: {
      CHATWIDGET: "StageViewsWidget-Chat"
    },
    KEYBOARDTIMEPICKER: {
      START: "KeyboardTimePicker-Start",
      STOP: "KeyboardTimePicker-Stop"
    }
  },
  VIEWS: {
    TASKS: {
      DRAWER: {},
      SHOWTEMPLATE: {},
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

export const DATACY_TASKS = DATACY.VIEWS.TASKS;
export const DATACY_KEYCLOAK = DATACY.KEYCLOAK;

