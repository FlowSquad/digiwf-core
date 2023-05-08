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
  SCREENS: {
    ADMINISTRATION: {
      ABBREVIATION: {
        AS: {
          NEW: "AbbreviationScreen-AbbreviationsTable-SettingsButton-Create",
          EDIT: "AbbreviationScreen-AbbreviationsTable-MenuItem-Edit",
          TRASH: "AbbreviationScreen-AbbreviationsTable-MenuItem-Trash",
          ABBREVIATIONS: "AbbreviationScreen-AbbreviationsTable-SettingsTable-TableContainer",
          POPUP: {
            NAME: "AbbreviationScreen-AbbreviationsTable-AbbreviationFormDialog-Edit-SettingsTextField-Short",
            LONGNAME: "AbbreviationScreen-AbbreviationsTable-AbbreviationFormDialog-Edit-SettingsTextField-Long",
            SAVE: "AbbreviationScreen-AbbreviationsTable-AbbreviationFormDialog-Edit-PopupDialog-Button-First",
            OVERWRITE: "AbbreviationScreen-AbbreviationsTable-AbbreviationFormDialog-Edit-AbbreviationsOverwritePrompt-Button-First",
            TRASH: "AbbreviationScreen-AbbreviationsTable-CustomDialog-Delete-PopupDialog-Button-First",
            CONFIRM: "AbbreviationScreen-AbbreviationsTable-AbbreviationFormDialog-Edit-PopupDialog-Button-First",
            CONFIRMOVERWRITE: "AbbreviationScreen-AbbreviationsTable-AbbreviationFormDialog-Edit-AbbreviationsOverwritePrompt-Button-Second"
          }
        }
      },
      ATTRIBUTE: {
        AS: {
          EDIT: "AttributeScreen-AttributeTable-IconButton-Edit",
          TRASH: "AttributeScreen-AttributeTable-IconButton-Trash",
          NEW: "AttributeScreen-SettingsButton-New",
          SECTIONS: "AttributeScreen-AttributeTable-SettingsSortableTable",
          POPUP: {
            TRASH: "AttributeScreen-DeleteSectionDialog-PopupDialog-Button-First"
          }
        },
        CAS: {
          ALERT: "CreateAttributeScreen-AttributeForm-Toast",
          NAME: "CreateAttributeScreen-AttributeForm-SettingsTextField-Name",
          DESC: "CreateAttributeScreen-AttributeForm-SettingsTextField-Description",
          DEFAULT: "CreateAttributeScreen-AttributeForm-Default",
          RESPONSIBILITY: "CreateAttributeScreen-AttributeForm-Checkbox-Responsibility",
          SEARCH: "CreateAttributeScreen-AttributeForm-Checkbox-Search",
          KEY: "CreateAttributeScreen-AttributeForm-SettingsTextField-Key",
          PROPERTY: "CreateAttributeScreen-AttributeForm-Select-Property",
          TYPE: "CreateAttributeScreen-AttributeForm-Select-Type",
          TREE: "CreateAttributeScreen-AttributeForm-Select-Tree",
          SAVE: "CreateAttributeScreen-AttributeForm-SettingsButton-Save"
        },
        CSS: {
          ALERT: "CreateSectionScreen-Toast-Error",
          NAME: "CreateSectionScreen-SettingsTextField-Name",
          DESC: "CreateSectionScreen-SettingsTextField-Description",
          SAVE: "CreateSectionScreen-SettingsButton-Save"
        },
        CSCS: {
          ALERT: "CreateStatusConfigScreen-Toast",
          STATUS: "CreateStatusConfigScreen-SettingsSelect-Status",
          HIDDEN: "CreateStatusConfigScreen-SettingsCheckbox-Hidden",
          PROTECTED: "CreateStatusConfigScreen-SettingsCheckbox-Protected",
          SAVE: "CreateStatusConfigScreen-SettingsButton-Save"
        },
        EAS: {
          ASCE: {
            EDIT: "EditAttributeScreen-AttributeStatusConfigurationEditor-IconButton-Edit",
            TRASH: "EditAttributeScreen-AttributeStatusConfigurationEditor-IconButton-Trash",
            NEW: "EditAttributeScreen-AttributeStatusConfigurationEditor-SettingsButton-New",
            STATES: "EditAttributeScreen-AttributeStatusConfigurationEditor-SettingsTableContainer",
            POPUP: {
              TRASH: "EditAttributeScreen-AttributeStatusConfigurationEditor-DeleteStatusConfigDialog-PopupDialog-Button-First"
            }
          },
          AME: {
            SEARCH: "EditAttributeScreen-AttributeMetadataEditor-AttributeForm-Checkbox-Search",
            DEFAULT: "EditAttributeScreen-AttributeMetadataEditor-AttributeForm-Default-StringField",
            RESPONSIBILITY: "EditAttributeScreen-AttributeMetadataEditor-AttributeForm-Checkbox-Responsibility",
            SAVE: "EditAttributeScreen-AttributeMetadataEditor-AttributeForm-SettingsButton-Save"
          }
        },
        ESS: {
          SAE: {
            EDIT: "EditSectionScreen-SectionAttributesEditor-IconButton-Edit",
            TRASH: "EditSectionScreen-SectionAttributesEditor-IconButton-Trash",
            NEW: "EditSectionScreen-SectionAttributesEditor-SettingsButton-New",
            ATTRIBUTES: "EditSectionScreen-SectionAttributesEditor-SettingsSortableTable",
            POPUP: {
              TRASH: "EditSectionScreen-SectionAttributesEditor-DeleteAttributeDialog-PopupDialog-Button-First"
            }
          },
          SME: {
            ALERT: "EditSectionScreen-SectionMetadataEditor-Toast",
            NAME: "EditSectionScreen-SectionMetadataEditor-SettingsTextField-Name",
            DESC: "EditSectionScreen-SectionMetadataEditor-SettingsTextField-Description",
            SAVE: "EditSectionScreen-SectionMetadataEditor-SettingsButton-Save",
          },
        },
        ESCS: {}
      },
      AUTOMATICPRINTING: {
        APS: {
          NEWCONFIG: "AutomaticPrintingScreen-PrintConfigurationsEditor-SettingsButton-NewConfiguration",
          CONFIGS: "AutomaticPrintingScreen-PrintConfigurationsEditor-PrintConfigurationsTable-SettingsTableContainer",
          TRASHCONFIG: "AutomaticPrintingScreen-PrintConfigurationsEditor-PrintConfigurationsTable-IconButton-Trash",
          EDITCONFIG: "AutomaticPrintingScreen-PrintConfigurationsEditor-PrintConfigurationsTable-IconButton-Edit",
          POPUP: {
            TRASHCNF: "AutomaticPrintingScreen-PrintConfigurationsEditor-DeletePrintConfigurationDialog-PopupDialog-Button-First",
            TRASHPG: "AutomaticPrintingScreen-PrinterGroupsEditor-DeletePrinterGroupDialog-PopupDialog-Button-First"
          },
          NEWPG: "AutomaticPrintingScreen-PrinterGroupsEditor-SettingsButton-NewPrinterGroup",
          GROUPS: "AutomaticPrintingScreen-PrinterGroupsEditor-PrinterGroupsTable-SettingsTableContainer",
          TRASHGROUP: "AutomaticPrintingScreen-PrinterGroupsEditor-PrinterGroupsTable-IconButton-Trash",
          EDITGROUP: "AutomaticPrintingScreen-PrinterGroupsEditor-PrinterGroupsTable-IconButton-Edit",
        },
        EPCS: {},
        CPCS: {
          NAME: "CreatePrintConfigurationScreen-SettingsTextField-Name",
          DESC: "CreatePrintConfigurationScreen-SettingsTextField-Description",
          TEMPLATE: "CreatePrintConfigurationScreen-SettingsSelect-Template",
          COUNT: "CreatePrintConfigurationScreen-SettingsTextField-CopyCount",
          SAVE: "CreatePrintConfigurationScreen-SettingsButton-Save"
        },
        CPS: {
          NAME: "CreatePrinterScreen-SettingsTextField-Name",
          DESC: "CreatePrinterScreen-SettingsTextField-Description",
          SAVE: "CreatePrinterScreen-SettingsButton-Save"
        },
        EPS: {},
        CPGS: {
          NAME: "CreatePrinterGroupScreen-SettingsTextField-Name",
          DESC: "CreatePrinterGroupScreen-SettingsTextField-Description",
          SAVE: "CreatePrinterGroupScreen-SettingsButton-Save"
        },
        EPGS: {
          CREATE: "EditPrinterGroupScreen-PrinterGroupPrintersEditor-SettingsButton-NewPrinter",
          PRINTERS: "EditPrinterGroupScreen-PrinterGroupPrintersEditor-PrintersTable-SettingsTableContainer",
          EDIT: "EditPrinterGroupScreen-PrinterGroupPrintersEditor-PrintersTable-IconButton-Edit",
          TRASH: "EditPrinterGroupScreen-PrinterGroupPrintersEditor-PrintersTable-IconButton-Trash",
          POPUP: {
            TRASH: "EditPrinterGroupScreen-PrinterGroupPrintersEditor-DeletePrinterDialog-PopupDialog-Button-First",
          },
        }
      },
      CHAT: {
        CCS: {
          ARCHIVE: "ChatConfigScreen-ChatGroupsTable-SettingsCheckbox-Archive",
          PRIVATE: "ChatConfigScreen-ChatGroupsTable-SettingsCheckbox-Private",
          SEARCH: "ChatConfigScreen-ChatGroupsTable-TextField-Search",
          TABLE: {
            ROOT: "ChatConfigScreen-ChatGroupsTable-SettingsTable-TableContainer",
            MENU: {
              ROOT: "ChatConfigScreen-ChatGroupsTable-IconButton",
              ITEM: "ChatConfigScreen-ChatGroupsTable-MenuItem",
              POPUP: {
                ARCHIVE: "ChatConfigScreen-ChatGroupsTable-CustomDialog-Archive-PopupDialog-Button-First",
                ARCHIVEUNDO: "ChatConfigScreen-ChatGroupsTable-CustomDialog-Archive-Undo-PopupDialog-Button-First",
                DELETE: "ChatConfigScreen-ChatGroupsTable-CustomDialog-Delete-PopupDialog-Button-First"
              }
            }
          },
          NEW: "ChatConfigScreen-SettingsButton-New",
          ALL: {
            ROOT: "ChatConfigScreen-ChatHistoryEditor-SettingsButton-DeleteAll",
            POPUP: {
              DELETE: "ChatConfigScreen-ChatHistoryEditor-DeleteAllMessagesDialog-PopupDialog-Button-First"
            }
          },
          HISTORY: "ChatConfigScreen-ChatHistoryEditor-SettingsForm"
        },
        CGES: {
          TITLE: "ChatGroupEditScreen-SettingsTextField-Title",
          SAVE: "ChatGroupEditScreen-SettingsButton-Save",
          SEARCH: "ChatGroupEditScreen-ChatUsersEditor-TextField-Search",
          MEMBER: "ChatGroupEditScreen-ChatUsersEditor-Checkbox",
          WRITE: "ChatGroupEditScreen-ChatUsersEditor-Checkbox"
        },
        CGCS: {
          TITLE: "ChatGroupCreateScreen-SettingsTextField-Title",
          SEARCH: "ChatGroupCreateScreen-ChatUsersEditor-TextField-Search",
          MEMBER: "ChatGroupCreateScreen-ChatUsersEditor-Checkbox-Member",
          SAVE: "ChatGroupCreateScreen-SettingsButton-Save"
        }
      },
      DEFINITION: {
        DS: {
          EDIT: "DefinitionScreen-DefinitionTable-IconButton-Edit",
          TRASH: "DefinitionScreen-DefinitionTable-IconButton-Trash",
          NEW: "DefinitionScreen-SettingsButton-New",
          DEFINITIONS: "DefinitionScreen-DefinitionTable-SettingsTableContainer",
          POPUP: {
            TRASH: "DefinitionScreen-DeleteDefinitionDialog-PopupDialog-Button-First"
          }
        },
        CBVS: {},
        CDS: {
          NAME: "CreateDefinitionScreen-SettingsTextField-Name",
          DESC: "CreateDefinitionScreen-SettingsTextField-Description",
          SAVE: "CreateDefinitionScreen-SettingsButton-Save"
        },
        CVS: {
          NAME: "CreateValueScreen-SettingsTextField-Name",
          SYMBOL: "CreateValueScreen-SettingsTextField-Symbol",
          ACCESS: "CreateValueScreen-Permissions",
          RESPONSIBILITY: "CreateValueScreen-Responsibilities",
          LOCK: "CreateValueScreen-Checkbox-Lock",
          SAVE: "CreateValueScreen-SettingsButton-Save",
          POPUP: {
            ACCESS: {
              EXPAND: "",
              CHECK: ""
            },
            RESPONSIBILITY: {
              CHECK: ""
            },
            SAVE: ""
          }
        },
        EDS: {
          DVE: {
            NEW: "EditDefinitionScreen-DefinitionValuesEditor-SettingsButton-New",
            BATCHNEW: "EditDefinitionScreen-DefinitionValuesEditor-SettingsButton-Batch-New",
            EDIT: "EditDefinitionScreen-DefinitionValuesEditor-IconButton-Edit",
            TRASH: "EditDefinitionScreen-DefinitionValuesEditor-IconButton-Trash",
            VALUES: "EditDefinitionScreen-DefinitionValuesEditor-SettingsTableContainer",
            SHOWARCHIVED: "EditDefinitionScreen-DefinitionValuesEditor-SettingsCheckbox",
            POPUP: {
              TRASH: "EditDefinitionScreen-DefinitionValuesEditor-DeleteValueDialog-PopupDialog-Button-First"
            }
          },
          DME: {
            ALERT: "EditDefinitionScreen-DefinitionMetadataEditor-Toast",
            NAME: "EditDefinitionScreen-DefinitionMetadataEditor-SettingsTextField-Name",
            DESC: "EditDefinitionScreen-DefinitionMetadataEditor-SettingsTextField-Description",
            SAVE: "EditDefinitionScreen-DefinitionMetadataEditor-SettingsButton-Save"
          }
        },
        EVS: {
          VME: {
            ALERT: "EditValueScreen-ValueMetadataEditor-Toast",
            NAME: "EditValueScreen-ValueMetadataEditor-SettingsTextField-Name",
            SYMBOL: "EditValueScreen-ValueMetadataEditor-SettingsTextField-Symbol",
            ACCESS: "EditValueScreen-ValueMetadataEditor-TreeSelect-Permission",
            RESPONSIBILITY: "EditValueScreen-ValueMetadataEditor-TreeSelect-Responsibility",
            LOCK: "EditValueScreen-ValueMetadataEditor-Checkbox-Lock",
            SAVE: "EditValueScreen-ValueMetadataEditor-SettingsButton-Save",
            POPUP: {
              ACCESS: {
                CHECK: "EditValueScreen-ValueMetadataEditor-TreeSelect-Permission-TreePopup-TreeSelect-TreeNodeView-TreeNodeItem-Checkbox",
                SEARCH: "EditValueScreen-ValueMetadataEditor-TreeSelect-Permission-TreePopup-TreeSelect-TextField-Search"
              },
              RESPONSIBILITY: {
                CHECK: "EditValueScreen-ValueMetadataEditor-TreeSelect-Responsibility-TreePopup-TreeSelect-TreeNodeView-TreeNodeItem-Checkbox"
              },
              RSAVE: "EditValueScreen-ValueMetadataEditor-TreeSelect-Responsibility-TreePopup-TreeSelect-SettingsButton-Save",
              PSAVE: "EditValueScreen-ValueMetadataEditor-TreeSelect-Permission-TreePopup-TreeSelect-SettingsButton-Save"
            }
          }
        }
      },
      EDITOR: {
        ES: {
          NSCE: {
            ALERT: "EditorScreen-NextStatusConfigurationEditor-Toast",
            TRANSIT: {
              ROOT: "EditorScreen-NextStatusConfigurationEditor-Select-Transit",
              ITEMS: {
                SCREEN: "back-to-screen",
                PREVIOUS: "previous-question",
                NEXT: "next-question"
              }
            },
            SAVE: "EditorScreen-NextStatusConfigurationEditor-SettingsButton-Save"
          },
          CCE: {
            ALERT: "EditorScreen-CommentConfigurationEditor-Toast",
            COMMENT: {
              ROOT: "EditorScreen-CommentConfigurationEditor-Select-Comment",
              ITEMS: {}
            },
            VISIBILITY: {
              ROOT: "EditorScreen-CommentConfigurationEditor-Select-Visibility",
              ITEMS: {}
            },
            SAVE: "EditorScreen-CommentConfigurationEditor-SettingsButton-Save"
          },
          ECE: {
            ALERT: "EditorScreen-EditorConfigurationEditor-Toast",
            WIDTH: "EditorScreen-EditorConfigurationEditor-SettingsTextField-Width",
            PREVIEW: "EditorScreen-EditorConfigurationEditor-Checkbox-Show",
            ECE: {
              CHECK: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-Checkbox",
              FONT: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-Select-FontType",
              SIZE: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-Select-Size",
              HEIGHT: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-SettingsTextField-Height",
              ALIGNL: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-ToggleButton-Left",
              ALIGNC: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-ToggleButton-Center",
              ALIGNR: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-ToggleButton-Right",
              ALIGNJ: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-ToggleButton-Justify",
              BOLD: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-ToggleButton-Bold",
              ITALIC: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-ToggleButton-Italic",
              ULINE: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-ToggleButton-Underlined",
              STRIKE: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-ToggleButton-StrikeThrough",
              LHEIGHT: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-IconSelect-LineHeight",
              FCOLOR: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-ColorDropDown-FontColor",
              BCOLOR: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-ColorDropDown-BColor",
              MARGINL: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-SettingsTextField-MarginLeft",
              MARGINT: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-SettingsTextField-MarginTop",
              MARGINB: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-SettingsTextField-MarginBottom",
              MARGINR: "EditorScreen-EditorConfigurationEditor-EditorConfigurationEntry-SettingsTextField-MarginRight"
            },
            SAVE: "EditorScreen-EditorConfigurationEditor-SettingsButton-Save"
          }
        }
      },
      FUNCTION: {
        FS: {
          FUNCTIONS: "FunctionScreen-FunctionTable-SettingsTableContainer",
          EDIT: "FunctionScreen-FunctionTable-IconButton-Edit",
          TRASH: "FunctionScreen-FunctionTable-IconButton-Trash",
          NEW: "FunctionScreen-SettingsButton-New",
          POPUP: {
            TRASH: "FunctionScreen-DeleteFunctionDialog-PopupDialog-Button-First"
          }
        },
        CFS: {
          ALERT: "CreateFunctionScreen-Toast-Error",
          NAME: "CreateFunctionScreen-SettingsTextField-Name",
          DEFAULT: "CreateFunctionScreen-Checkbox-Default",
          SAVE: "CreateFunctionScreen-SettingsButton-Save"
        },
        EFS: {
          ALERT: "EditFunctionScreen-FunctionMetadataEditor-Toast",
          NAME: "EditFunctionScreen-FunctionMetadataEditor-SettingsTextField-Name",
          DEFAULT: "EditFunctionScreen-FunctionMetadataEditor-Checkbox-Default",
          SAVE: "EditFunctionScreen-FunctionMetadataEditor-SettingsButton-Save"
        }
      },
      LANGUAGE: {
        LS: {
          EDIT: "LanguageScreen-LanguageTable-IconButton-Edit",
          TRASH: "LanguageScreen-LanguageTable-IconButton-Trash",
          LANGUAGES: "LanguageScreen-LanguageTable-SettingsSortableTable",
          NEW: "LanguageScreen-SettingsButton-New",
          POPUP: {
            TRASH: "LanguageScreen-DeleteLanguageDialog-PopupDialog-Button-First"
          }
        },
        CLS: {
          TITLE: "CreateLanguageScreen-LanguageForm-SettingsTextField-Title",
          SELECT: "CreateLanguageScreen-LanguageForm-Select-Icon",
          SAVE: "CreateLanguageScreen-LanguageForm-SettingsButton-Save"
        },
        ELS: {
          TITLE: "EditLanguageScreen-LanguageForm-SettingsTextField-Title",
          SELECT: "EditLanguageScreen-LanguageForm-Select-Icon",
          DEFAULT: "EditLanguageScreen-LanguageForm-Checkbox-Default",
          SAVE: "EditLanguageScreen-LanguageForm-SettingsButton-Save",
          TRANSLATION: {
            OPEN: "EditLanguageScreen-LanguageTextsEditor-StaticTranslationEditor-TranslationEditor",
            NEW: "EditLanguageScreen-LanguageTextsEditor-StaticTranslationEditor-TranslationEditorRow-Translation",
            SAVE: "EditLanguageScreen-LanguageTextsEditor-SettingsButton-Save"
          }
        }
      },
      LIVE: {
        LCS: {
          ATTRIBUTE: "LiveConfigScreen-LiveConfigurationEditor-Attribute",
          GROUPING: "LiveConfigScreen-LiveConfigurationEditor-Grouping",
          SAVE: "LiveConfigScreen-LiveConfigurationEditor-SettingsButton-Save",
        }
      },
      MENU: {
        MS: {
          ALERT: "MenuScreen-LogoEditor-Toast",
          LOGO: "MenuScreen-LogoEditor-Select-Logo",
          SAVE: "MenuScreen-LogoEditor-SettingsButton-Save",
          REMOVE: "MenuScreen-LogoEditor-SettingsButton-Remove"
        }
      },
      PAGEDEFINITION: {
        PDS: {
          PAGES: "PageDefinitionScreen-PageDefinitionTable-SettingsSortableTable",
          NEW: "PageDefinitionScreen-SettingsButton-New",
          EDIT: "PageDefinitionScreen-PageDefinitionTable-IconButton-Edit",
          TRASH: "PageDefinitionScreen-PageDefinitionTable-IconButton-Trash",
          POPUP: {
            TRASH: "PageDefinitionScreen-DeletePageDefinitionDialog-PopupDialog-Button-First"
          }
        },
        CPDFS: {
          NAME: "CreatePageDefinitionFilterScreen-PageDefinitionFilterForm-SettingsTextField-Name",
          DESC: "CreatePageDefinitionFilterScreen-PageDefinitionFilterForm-SettingsTextField-Description",
          SAVE: "CreatePageDefinitionFilterScreen-PageDefinitionFilterForm-SettingsButton-Save"
        },
        CPDFWS: {
          SAVE: "CreatePageDefinitionFilterWertScreen-SettingsButton-Save",
          NEW: "CreatePageDefinitionFilterWertScreen-SettingsButton-New",
          TYPE: "CreatePageDefinitionFilterWertScreen-Select-Type",
          STATUS: {
            OP: "CreatePageDefinitionFilterWertScreen-StatusFilterValue-Operator",
            ENUMSELECT: "CreatePageDefinitionFilterWertScreen-StatusFilterValue-EnumSelect",
            CHECK: "CreatePageDefinitionFilterWertScreen-StatusFilterValue-EnumSelect-EnumPopup-EnumView-EnumItem-Checkbox",
            SAVE: "CreatePageDefinitionFilterWertScreen-StatusFilterValue-EnumSelect-EnumPopup-SettingsButton-Save"
          },
          ATTRIBUTE: {
            OP: "CreatePageDefinitionFilterWertScreen-AttributeFilterValue-Operator",
            SELECT: "CreatePageDefinitionFilterWertScreen-AttributeFilterValue-Select-Attribute",
            ENUMSELECT: "CreatePageDefinitionFilterWertScreen-AttributeFilterValue-EnumSelect",
            CHECK: "CreatePageDefinitionFilterWertScreen-AttributeFilterValue-EnumSelect-EnumPopup-EnumView-EnumItem-Checkbox",
            SAVE: "CreatePageDefinitionFilterWertScreen-AttributeFilterValue-EnumSelect-EnumPopup-SettingsButton-Save"
          }
        },
        CPDS: {
          ALERT: "CreatePageDefinitionScreen-PageDefinitionForm-Toast",
          NAME: "CreatePageDefinitionScreen-PageDefinitionForm-SettingsTextField-Name",
          DESC: "CreatePageDefinitionScreen-PageDefinitionForm-SettingsTextField-Description",
          KEY: "CreatePageDefinitionScreen-PageDefinitionForm-SettingsTextField-Key",
          COLOR: "CreatePageDefinitionScreen-PageDefinitionForm-Color",
          QLA: "CreatePageDefinitionScreen-PageDefinitionForm-Select-List",
          SLA: "CreatePageDefinitionScreen-PageDefinitionForm-Select-Search",
          SAVE: "CreatePageDefinitionScreen-PageDefinitionForm-SettingsButton-Save"
        },
        EPDFS: {
          NEW: "EditPageDefinitionFilterScreen-PageDefinitionFilterWerteEditor-SettingsButton-New",
          SAVEVALUE: "EditPageDefinitionFilterScreen-PageDefinitionFilterWerteEditor-SettingsButton-Save",
          SAVE: "EditPageDefinitionFilterScreen-PageDefinitionFilterForm-SettingsButton-Save",
        },
        EPDS: {
          ALERT: "EditPageDefinitionScreen-PageDefinitionForm-Toast",
          DESC: "EditPageDefinitionScreen-PageDefinitionForm-SettingsTextField-Description",
          KEY: "EditPageDefinitionScreen-PageDefinitionForm-SettingsTextField-Key",
          SAVE: "EditPageDefinitionScreen-PageDefinitionForm-SettingsButton-Save",
          NEW: "EditPageDefinitionScreen-SettingsButton-New",
          ROLE: "EditPageDefinitionScreen-PageDefinitionRolesEditor-Checkbox",
          FV: "EditPageDefinitionFilterScreen-PageDefinitionFilterWerteEditor-PageDefinitionFilterWertTable-SettingsTableContainer",
          QLA: "EditPageDefinitionScreen-PageDefinitionForm-Select-List"
        }
      },
      PRINTSORT: {
        PSS: {
          ALERT: "PrintSortScreen-PrintTreeEditor-Toast",
          ATTRIBUTES: "PrintSortScreen-PrintTreeEditor-PrintTreeTable-SettingsSortTable",
          ADD: "PrintSortScreen-PrintTreeEditor-SettingsButton-NewAttribute",
          SAVE: "PrintSortScreen-PrintTreeEditor-SettingsButton-Save",
          TRASH: "PrintSortScreen-PrintTreeEditor-PrintTreeTable-IconButton-Trash",
          POPUP: {
            ADD: "PrintSortScreen-PrintTreeEditor-AddAttributeDialog-PopupDialog-Button-First",
            SELECT: "PrintSortScreen-PrintTreeEditor-AddAttributeDialog-Select-Attribute",
            TRASH: "PrintSortScreen-PrintTreeEditor-DeleteAttributeDialog-PopupDialog-Button-First"
          }
        }
      },
      QUESTION: {
        QS: {
          TITLE: "QuestionScreen-MergeQuestionsConfigurationEditor-TemplateTextWidget-Title",
          QUESTION: "QuestionScreen-MergeQuestionsConfigurationEditor-TemplateTextWidget-Question",
          ANSWER: "QuestionScreen-MergeQuestionsConfigurationEditor-TemplateTextWidget-Answer",
          SAVE: "QuestionScreen-MergeQuestionsConfigurationEditor-SettingsButton-Save"
        }
      },
      QUESTIONLIST: {
        QLS: {
          LISTS: "QuestionListScreen-QuestionListTable-SettingsTableContainer",
          NEW: "QuestionListScreen-SettingsButton-New",
          TRASH: "QuestionListScreen-QuestionListTable-IconButton-Trash",
          EDIT: "QuestionListScreen-QuestionListTable-IconButton-Edit",
          POPUP: {
            TRASH: "QuestionListScreen-DeleteQuestionListDialog-PopupDialog-Button-First"
          }
        },
        CCS: {
          DATEFORMAT: "CreateColumnScreen-ColumnForm-SettingsSelect-DateFormat",
          TITLEICON: "CreateColumnScreen-ColumnForm-SettingsSelect-TitleIcon",
          TYPE: "CreateColumnScreen-ColumnForm-SettingsSelect-Type",
          NAME: "CreateColumnScreen-ColumnForm-SettingsTextField-Title",
          CENTER: "CreateColumnScreen-ColumnForm-ToggleButton-Center",
          RIGHT: "CreateColumnScreen-ColumnForm-ToggleButton-Right",
          WIDTH: "CreateColumnScreen-ColumnForm-SettingsTextField-Width",
          MINWIDTH: "CreateColumnScreen-ColumnForm-SettingsTextField-MinWidth",
          SORT: "CreateColumnScreen-ColumnForm-SettingsCheckbox-Sortable",
          SAVE: "CreateColumnScreen-ColumnForm-SettingsButton-Save"
        },
        CQLS: {
          TOAST: "CreateQuestionListScreen-QuestionListForm-Toast",
          NAME: "CreateQuestionListScreen-QuestionListForm-SettingsTextField-Name",
          DESC: "CreateQuestionListScreen-QuestionListForm-SettingsTextField-Description",
          TYPE: "CreateQuestionListScreen-QuestionListForm-SettingsSelect-Type",
          SORT: "CreateQuestionListScreen-QuestionListForm-SettingsSelect-Sort",
          SORTTYPE: "CreateQuestionListScreen-QuestionListForm-SettingsSelect-SortType",
          SIZES: "CreateQuestionListScreen-QuestionListForm-SettingsTextField-Sizes",
          DEFAULTSIZE: "CreateQuestionListScreen-QuestionListForm-SettingsTextField-DefaultSize",
          LINEHEIGHT: "CreateQuestionListScreen-QuestionListForm-SettingsTextField-LineHeight",
          SAVE: "CreateQuestionListScreen-QuestionListForm-SettingsButton-Save"
        },
        EQLS: {
          NAME: "EditQuestionListScreen-QuestionListForm-SettingsTextField-Name",
          DESC: "EditQuestionListScreen-QuestionListForm-SettingsTextField-Description",
          LINEHEIGHT: "EditQuestionListScreen-QuestionListForm-SettingsTextField-LineHeight",
          TYPE: "EditQuestionListScreen-QuestionListForm-SettingsSelect-Type",
          SORT: "EditQuestionListScreen-QuestionListForm-SettingsSelect-Sort",
          SORTTYPE: "EditQuestionListScreen-QuestionListForm-SettingsSelect-SortType",
          SIZES: "EditQuestionListScreen-QuestionListForm-SettingsTextField-Sizes",
          DEFAULTSIZE: "EditQuestionListScreen-QuestionListForm-SettingsTextField-DefaultSize",
          SAVE: "EditQuestionListScreen-QuestionListForm-SettingsButton-Save",
          TOAST: "EditQuestionListScreen-QuestionListForm-Toast",
          QLCE: {
            COLUMNS: "EditQuestionListScreen-QuestionListColumnsEditor-SettingsTableContainer",
            NEW: "EditQuestionListScreen-QuestionListColumnsEditor-SettingsButton-NewColumn",
            EDIT: "EditQuestionListScreen-QuestionListColumnsEditor-IconButton-Edit",
            TRASH: "EditQuestionListScreen-QuestionListColumnsEditor-IconButton-Trash",
            EXPORT: "EditQuestionListScreen-QuestionListColumnsEditor-SettingsButton-ExportColumn",
            IMPORT: "EditQuestionListScreen-QuestionListColumnsEditor-SettingsButton-ImportColumn",
            IMPORTDLG: {
              STRING: "EditQuestionListScreen-QuestionListColumnsEditor-QuestionListImportDialog-TextField-InsertConfiguration",
              IMPORT: "EditQuestionListScreen-QuestionListColumnsEditor-QuestionListImportDialog-PopupDialog-Button-First"
            },
            EXPORTDLG: {
              PRE: "EditQuestionListScreen-QuestionListColumnsEditor-QuestionListExportDialog-Pre",
              READY: "EditQuestionListScreen-QuestionListColumnsEditor-QuestionListExportDialog-PopupDialog-Button-First",
              COPY: "EditQuestionListScreen-QuestionListColumnsEditor-QuestionListExportDialog-PopupDialog-Button-Second"
            },
            DLG: {
              DELETE: "EditQuestionListScreen-QuestionListColumnsEditor-DeleteColumnDialog-PopupDialog-Button-First"
            }
          }
        },
        ECS: {
          DATEFORMAT: "EditColumnScreen-ColumnForm-SettingsSelect-DateFormat",
          TITLEICON: "EditColumnScreen-ColumnForm-SettingsSelect-TitleIcon",
          TYPE: "EditColumnScreen-ColumnForm-SettingsSelect-Type",
          NAME: "EditColumnScreen-ColumnForm-SettingsTextField-Title",
          RIGHT: "EditColumnScreen-ColumnForm-ToggleButton-Right",
          WIDTH: "EditColumnScreen-ColumnForm-SettingsTextField-Width",
          MINWIDTH: "EditColumnScreen-ColumnForm-SettingsTextField-MinWidth",
          SORT: "EditColumnScreen-ColumnForm-SettingsCheckbox-Sortable",
          SAVE: "EditColumnScreen-ColumnForm-SettingsButton-Save"
        }
      },
      ROLE: {
        RS: {
          NEW: "RoleScreen-SettingsButton-New",
          ROLES: "RoleScreen-RoleTable-SettingsTableContainer",
          TRASH: "RoleScreen-RoleTable-SettingsTableButton-Trash",
          EDIT: "RoleScreen-RoleTable-SettingsTableButton-Edit",
          POPUP: {
            TRASH: "RoleScreen-DeleteRoleDialog-PopupDialog-Button-First"
          }
        },
        CFS: {
          EDIT: "CreateFilterScreen-SettingsCheckbox-Editable",
          VISIBLE: "CreateFilterScreen-SettingsCheckbox-Visible",
          EMAIL: "CreateFilterScreen-SettingsCheckbox-Email",
          FORWARD: "CreateFilterScreen-SettingsCheckbox-Forward",
          NEW: "CreateFilterScreen-SettingsButton-New"
        },
        CFVS: {
          TYPE: "CreateFilterValueScreen-SettingsSelect-Type",
          OPERATOR: "CreateFilterValueScreen-SettingsSelect-Operator",
          STATUSVALUE: "CreateFilterValueScreen-SettingsSelect-StatusValue",
          NEW: "CreateFilterValueScreen-SettingsButton-New"
        },
        CRS: {
          TOAST: "CreateRoleScreen-Toast",
          NAME: "CreateRoleScreen-SettingsTextField-Name",
          FUNCTION: "CreateRoleScreen-SettingsSelect-Function",
          STATUS: "CreateRoleScreen-SettingsSelect-StartStatus",
          NEW: "CreateRoleScreen-SettingsButton-New"
        },
        ERS: {
          TOAST: "EditRoleScreen-RoleMetadataEditor-Toast",
          NAME: "EditRoleScreen-RoleMetadataEditor-SettingsTextField-Name",
          FUNCTION: "EditRoleScreen-RoleMetadataEditor-SettingsSelect-Function",
          STATUS: "EditRoleScreen-RoleMetadataEditor-SettingsSelect-StartStatus",
          SAVE: "EditRoleScreen-RoleMetadataEditor-SettingsButton-Save",
          PERMISSION: "EditRoleScreen-RolePermissionsEditor-PermissionEditor-SettingsCheckbox",
          NEWFILTER: "EditRoleScreen-RoleFilterEditor-SettingsButton-Save",
          FILTERS: "EditRoleScreen-RoleFilterEditor-SettingsTableContainer",
          EDITFILTER: "EditRoleScreen-RoleFilterEditor-SettingsTableButton-Edit"
        },
        EFS: {
          EDIT: "EditFilterScreen-FilterMetadataEditor-SettingsCheckbox-Editable",
          VISIBLE: "EditFilterScreen-FilterMetadataEditor-SettingsCheckbox-Visible",
          EMAIL: "EditFilterScreen-FilterMetadataEditor-SettingsCheckbox-Email",
          FORWARD: "EditFilterScreen-FilterMetadataEditor-SettingsCheckbox-Forward",
          SAVE: "EditFilterScreen-FilterMetadataEditor-SettingsButton-Save",

          NEWVALUE: "EditFilterScreen-FilterValueEditor-SettingsButton-Save",
          FILTERVALUES: "EditFilterScreen-FilterValueEditor-SettingsTableContainer",
          TRASHFILTER: "EditFilterScreen-FilterValueEditor-SettingsTableButton-Trash",
          POPUP: {
            TRASH: "EditFilterScreen-RoleFilterEditor-DeleteFilterDialog-PopupDialog-Button-First"
          },
          SAVEVALUE: "EditFilterScreen-FilterValueEditor-SettingsButton-Save"
        },
      },
      SEARCHCONFIG: {
        SCS: {
          EDIT: "SearchConfigScreen-SearchConfigTable-SettingsTableButton-Edit",
          TRASH: "SearchConfigScreen-SearchConfigTable-SettingsTableButton-Trash",
          NEW: "SearchConfigScreen-SettingsButton-New",
          CONFIGS: "SearchConfigScreen-SearchConfigTable-SettingsTableContainer",
          POPUP: {
            TRASH: "SearchConfigScreen-DeleteSearchConfigDialog-PopupDialog-Button-First"
          }
        },
        CFS: {
          NAME: "CreateFieldScreen-FieldForm-SettingsTextField-Name",
          TYPE: "CreateFieldScreen-FieldForm-SettingsSelect-SelectType",
          SAVE: "CreateFieldScreen-FieldForm-SettingsButton-Save"
        },
        CSCS: {
          TOAST: "CreateSearchConfigScreen-SearchConfigForm-Toast",
          NAME: "CreateSearchConfigScreen-SearchConfigForm-SettingsTextField-Name",
          DESC: "CreateSearchConfigScreen-SearchConfigForm-SettingsTextField-Description",
          SAVE: "CreateSearchConfigScreen-SearchConfigForm-SettingsButton-Save"
        },
        ESCS: {
          TOAST: "EditSearchConfigScreen-SearchConfigForm-Toast",
          NAME: "EditSearchConfigScreen-SearchConfigForm-SettingsTextField-Name",
          DESC: "EditSearchConfigScreen-SearchConfigForm-SettingsTextField-Description",
          SAVE: "EditSearchConfigScreen-SearchConfigForm-SettingsButton-Save",
          EDIT: "EditSearchConfigScreen-SearchConfigFieldsEditor-SettingsTableButton-Edit",
          TRASH: "EditSearchConfigScreen-SearchConfigFieldsEditor-SettingsTableButton-Trash",
          NEW: "EditSearchConfigScreen-SearchConfigFieldsEditor-SettingsButton-New",
          FIELDS: "EditSearchConfigScreen-SearchConfigFieldsEditor-SettingsSortableTable",
          POPUP: {
            TRASH: "EditSearchConfigScreen-SearchConfigFieldsEditor-DeleteFieldDialog-PopupDialog-Button-First"
          }
        },
        EFS: {
          NAME: "EditFieldScreen-FieldForm-SettingsTextField-Name",
          TYPE: "EditFieldScreen-FieldForm-SettingsSelect-SelectType",
          SAVE: "EditFieldScreen-FieldForm-SettingsButton-Save"
        }
      },
      SPEAKER: {
        SS: {
          NEW: "SpeakerScreen-SettingsButton-New",
          TRASH: "SpeakerScreen-SpeakerTable-SettingsTableButton-Trash",
          EDIT: "SpeakerScreen-SpeakerTable-SettingsTableButton-Edit",
          POPUP: {
            TRASH: "SpeakerScreen-DeleteSpeakerDialog-PopupDialog-Button-First"
          },
          SPEAKERS: "SpeakerScreen-SpeakerTable-SettingsSortableTable",
          CSD: {
            NAME: "StageScreen-CreateSpeakingRequestDialog-SettingsTextField-Name",
            DESC: "StageScreen-CreateSpeakingRequestDialog-SettingsTextField-Description",
            SAVE: "StageScreen-CreateSpeakingRequestDialog-PopupDialog-Button-First"
          },
        },
        CSAS: {},
        CSS: {
          SALUT: "CreateSpeakerScreen-SpeakerAttributes-QuestionPropertiesPanelSection-EnumProperty-186a64ed-bf7c-4e39-9ac9-744d2e616611",
          FIRSTNAME: "CreateSpeakerScreen-SpeakerAttributes-QuestionPropertiesPanelSection-StringProperty-a70e27ef-63c8-4af6-8681-93ffb1199288",
          LASTNAME: "CreateSpeakerScreen-SpeakerAttributes-QuestionPropertiesPanelSection-StringProperty-9ee8df1d-d3a7-42ee-a457-6c8c70466b4c",
          CITY: "CreateSpeakerScreen-SpeakerAttributes-QuestionPropertiesPanelSection-StringProperty-6e543ac9-6fe5-485a-9a15-80fbc0f850fa",
          SAVE: "CreateSpeakerScreen-SettingsButton-Save"
        },
        ESAS: {},
        ESSS: {},
        ESS: {
          ALERT: "EditSpeakerScreen-SpeakerMetadataEditor-Toast",
          NAME: "EditSpeakerScreen-SpeakerMetadataEditor-SettingsTextField-Name",
          DESC: "EditSpeakerScreen-SpeakerMetadataEditor-SettingsTextField-Description",
          FIRSTNAME: "EditSpeakerScreen-SpeakerMetadataEditor-SpeakerAttributes-QuestionPropertiesPanelSection-StringProperty-a70e27ef-63c8-4af6-8681-93ffb1199288",
          LASTNAME: "EditSpeakerScreen-SpeakerMetadataEditor-SpeakerAttributes-QuestionPropertiesPanelSection-StringProperty-9ee8df1d-d3a7-42ee-a457-6c8c70466b4c",
          CITY: "EditSpeakerScreen-SpeakerMetadataEditor-SpeakerAttributes-QuestionPropertiesPanelSection-StringProperty-6e543ac9-6fe5-485a-9a15-80fbc0f850fa",
          SAVE: "EditSpeakerScreen-SpeakerMetadataEditor-SettingsButton-Save"
        }
      },
      STAGE: {
        SCS: {
          ATTRIBUTE: "StageConfigScreen-StageConfigurationEditor-Attribute",
          NEXT: "StageConfigScreen-StageConfigurationEditor-SettingsCheckbox-Next",
          GROUPING: "StageConfigScreen-StageConfigurationEditor-Grouping",
          SAVE: "StageConfigScreen-StageConfigurationEditor-SettingsButton-Save",
          SAVETEMPL: "StageConfigScreen-QuestionTemplateEditor-SettingsButtons-Save"
        }
      },
      STATUS: {
        SS: {
          NEW: "StatusScreen-SettingsButton-New",
          STATES: "StatusScreen-StatusTable-SettingsSortableTable",
          TRASH: "StatusScreen-StatusTable-SettingsTableButton-Trash",
          EDIT: "StatusScreen-StatusTable-SettingsTableButton-Edit",
          POPUP: {
            TRASH: "StatusScreen-DeleteStatusDialog-PopupDialog-Button-First"
          }
        },
        CSS: {
          TOAST: "CreateStatusScreen-Toast-Error",
          NAME: "CreateStatusScreen-SettingsTextField-Name",
          SAVE: "CreateStatusScreen-SettingsButton-Save"
        },
        ESS: {
          TOAST: "EditStatusScreen-StatusMetadataEditor-Toast",
          NAME: "EditStatusScreen-StatusMetadataEditor-SettingsTextField-Name",
          COLOR: "EditStatusScreen-StatusMetadataEditor-ColorPicker-Color",
          SAVE: "EditStatusScreen-StatusMetadataEditor-SettingsButton-Save",
          TRANSITIONS: "EditStatusScreen-StatusTransitionTable-SettingsSortableTable",
          TRASH: "EditStatusScreen-StatusTransitionTable-SettingsTableButton-Trash",
          EDIT: "EditStatusScreen-StatusTransitionTable-SettingsTableButton-Edit",
          POPUP: {
            TRASH: "EditStatusScreen-StatusTransitionTable-DeleteStatusTransitionDialog-PopupDialog-Button-First"
          },
          NEWTRANSITION: "EditStatusScreen-StatusTransitionTable-SettingsButton-Add",
          NEWFUNCTION: {
            TABLE: "EditStatusScreen-StatusFunctionButtonTable-SettingsSortableTable",
            TRASH: "EditStatusScreen-StatusFunctionButtonTable-SettingsTableButton-Trash",
            POPUP: {
              TRASH: "EditStatusScreen-StatusFunctionButtonTable-DeleteStatusFunctionButtonDialog-PopupDialog-Button-First"
            },
            BUTTON: "EditStatusScreen-StatusFunctionButtonTable-SettingsButton-Add",
            TITLE: "CreateStatusFunctionButtonScreen-SettingsTextField-Title",
            SAVE: "CreateStatusFunctionButtonScreen-SettingsButton-Save"
          }
        },
        CSFBS: {},
        ESFBS: {},
        CSTAS: {},
        CSTCS: {},
        CSTRFS: {},
        CSTS: {
          EDITOR: "CreateStatusTransitionScreen-SettingsCheckbox-Editor",
          TARGET: "CreateStatusTransitionScreen-SettingsSelect-Target",
          FUNCTION: "CreateStatusTransitionScreen-SettingsSelect-EditorFunction",
          NEW: "CreateStatusTransitionScreen-SettingsButton-New"
        },
        ESTS: {
          EDITOR: "EditStatusTransitionScreen-StatusTransitionMetadataEditor-SettingsCheckbox-Editor",
          TARGET: "EditStatusTransitionScreen-StatusTransitionMetadataEditor-SettingsSelect-Target",
          FUNCTION: "EditStatusTransitionScreen-StatusTransitionMetadataEditor-SettingsSelect-FunctionButtonId",
          SAVE: "EditStatusTransitionScreen-StatusTransitionMetadataEditor-SettingsButton-Save"
        }
      },
      SYSTEM: {
        SS: {}
      },
      TEMPLATE: {
        TS: {
          NEW: "TemplateScreen-SettingsButton-New",
          TEMPLATES: "TemplateScreen-TemplatesTable-SettingsSortableTable",
          EDIT: "TemplateScreen-TemplatesTable-SettingsTableButton-Edit",
          TRASH: "TemplateScreen-TemplatesTable-SettingsTableButton-Trash",
          POPUP: {
            TRASH: "TemplateScreen-DeleteTemplateDialog-PopupDialog-Button-First"
          }
        },
        CTS: {
          NAME: "CreateTemplateScreen-SettingsTextField-Name",
          TYP: "CreateTemplateScreen-Select-Type",
          SAVE: "CreateTemplateScreen-SettingsButton-Save"
        },
        CTXTS: {},
        ETS: {
          ALERT: "EditTemplateScreen-TemplateMetadataEditor-Toast",
          NAME: "EditTemplateScreen-TemplateMetadataEditor-SettingsTextField-Name",
          DESC: "EditTemplateScreen-TemplateMetadataEditor-SettingsTextField-Description",
          SAVE: "EditTemplateScreen-TemplateMetadataEditor-SettingsButton-Save"
        },
        ETXTS: {}
      },
      TREE: {
        TS: {
          NEW: "TreeScreen-SettingsButton-New",
          TREES: "TreeScreen-TreeTable-SettingsTableContainer-Trees",
          EDIT: "TreeScreen-TreeTable-SettingsTableButton-Edit",
          TRASH: "TreeScreen-TreeTable-SettingsTableButton-Trash",
          POPUP: {
            TRASH: "TreeScreen-DeleteTreeDialog-PopupDialog-Button-First"
          }
        },
        CNS: {
          VALUE: "CreateNodeScreen-SettingsTextField-Value",
          REPLACE: "CreateNodeScreen-SettingsButton-Replace",
          ALL: "CreateNodeScreen-SettingsButton-All",
          ADD: "CreateNodeScreen-SettingsButton-Add",
          SAVE: "CreateNodeScreen-SettingsButton-Save"
        },
        CTS: {
          TOAST: "CreateTreeScreen-Toast",
          NAME: "CreateTreeScreen-SettingsTextField-Name",
          DESC: "CreateTreeScreen-SettingsTextField-Description",
          TYPE: "CreateTreeScreen-SettingsSelect-Type",
          SELECTTYPE: "CreateTreeScreen-SettingsSelect-SelectType",
          VALUE: "CreateTreeScreen-SettingsSelect-Value",
          SAVE: "CreateTreeScreen-SettingsButton-Save"
        },
        ETS: {
          TOAST: "EditTreeScreen-TreeMetadataEditor-Toast",
          NAME: "EditTreeScreen-TreeMetadataEditor-SettingsTextField-Name",
          DESC: "EditTreeScreen-TreeMetadataEditor-SettingsTextField-Description",
          TYPE: "EditTreeScreen-TreeMetadataEditor-SettingsSelect-SelectionType",
          SAVE: "EditTreeScreen-TreeMetadataEditor-SettingsButton-Save",
          CREATE: "EditTreeScreen-TreeNodeEditor-TreeNodeView-TreeNodeRootItem-Create",
          NODES: {
            CREATE: "EditTreeScreen-TreeNodeEditor-TreeNodeView-TreeNodeItem-IconButton-Create",
            TREE: "EditTreeScreen-TreeNodeEditor-TreeNodeView-DragDropList-Container",
            ITEMS: "EditTreeScreen-TreeNodeEditor-TreeNodeView-TreeNodeItem",
            EDIT: "EditTreeScreen-TreeNodeEditor-TreeNodeView-TreeNodeItem-IconButton-Edit",
            TRASH: "EditTreeScreen-TreeNodeEditor-TreeNodeView-TreeNodeItem-IconButton-Trash",
            POPUP: {
              TRASH: "EditTreeScreen-TreeNodeEditor-DeleteNodeDialog-PopupDialog-Button-First"
            },
            UP: "EditTreeScreen-TreeNodeEditor-Button-Up",
            DOWN: "EditTreeScreen-TreeNodeEditor-Button-Down",
            CHEVRON: "EditTreeScreen-TreeNodeEditor-TreeNodeView-IconButton-Chevron"
          }
        },
        ENS: {
          NAME: "EditNodeScreen-SettingsTextField-Name",
          DESC: "EditNodeScreen-SettingsTextField-Description",
          SYMBOL: "EditNodeScreen-SettingsTextField-Symbol",
          VALUE: "EditNodeScreen-SettingsSelect-Value",
          SAVE: "EditNodeScreen-SettingsButton-Save"
        }
      },
      USER: {
        US: {
          NAME: "UserScreen-UserTable-StringField-Name",
          EDIT: "UserScreen-UserTable-SettingsTableButton-Edit"
        },
        EUS: {
          PERMISSION: "EditUserScreen-UserPermissionsEditor-PermissionsEditor-SettingsCheckbox",
          GROUP: "EditUserScreen-UserGroupsEditor-TreeNodeView-TreeNodeItem-Checkbox",
          ROLE: "EditUserScreen-UserRolesEditor-SettingsCheckbox",
          RCONTAINER: "EditUserScreen-UserRolesEditor-RolesContainer"
        }
      },
      AS: {
        SIDEMENU: {
          ABBREVIATION: "AdministrationScreen-MenuItem-Abkuerzungen",
          USER: "AdministrationScreen-MenuItem-User",
          FUNCTION: "AdministrationScreen-MenuItem-Function",
          ROLE: "AdministrationScreen-MenuItem-Role",
          ATTRIBUTE: "AdministrationScreen-MenuItem-Attribute",
          TREE: "AdministrationScreen-MenuItem-Tree",
          QU: "AdministrationScreen-MenuItem-Question",
          STATUS: "AdministrationScreen-MenuItem-Status",
          SPK: "AdministrationScreen-MenuItem-Speaker",
          DEF: "AdministrationScreen-MenuItem-Definition",
          STAGE: "AdministrationScreen-MenuItem-Stage",
          LIVE: "AdministrationScreen-MenuItem-Live",
          CHAT: "AdministrationScreen-MenuItem-Chat",
          EDIT: "AdministrationScreen-MenuItem-Edit",
          MNU: "AdministrationScreen-MenuItem-Menu",
          LNG: "AdministrationScreen-MenuItem-Language",
          AP: "AdministrationScreen-MenuItem-AutomaticPrinting",
          PS: "AdministrationScreen-MenuItem-PrintSort",
          PT: "AdministrationScreen-MenuItem-PrintTemplate",
          QUL: "AdministrationScreen-MenuItem-QuestionList",
          PD: "AdministrationScreen-MenuItem-PageDefinition",
          SEARCH: "AdministrationScreen-MenuItem-SearchConfig",
          SYS: "AdministrationScreen-MenuItem-System"
        }
      }
    },
    DYNAMIC: {
      DS: {
        SCREEN: "DynamicScreen",
        CHATSEND: "DynamicScreen-SidebarChatWidget-ChatWidget-IconButton-Send",
        CHATCLOSE: "DynamicScreen-SidebarChatWidget-ChatWidget-ChatGroupsView-ChatWidgetHeaderIconButton-Close",
        STATUSWIDGET: "DynamicScreen-StatusWidget",
        RELOAD: "DynamicScreen-Button-Reload",
        PRINT: {
          BUTTON: "DynamicScreen-Button-Print",
          DLG: {
            CONTENT: "DynamicScreen-PrintQuestionsDialog-RadioGroup",
            CANCEL: "DynamicScreen-PrintQuestionsDialog-PopupDialog-Button-Second"
          }
        },
        MERGE: {
          BUTTON: "DynamicScreen-Button-Merge",
          DLG: "DynamicScreen-MergeQuestionsDialog-PopupDialog-Button-First"
        },
        BATCH: {
          DLG: {
            SAVE: "DynamicScreen-BatchDialog-PopupDialog-Button-First"
          },
          VALUE: "DynamicScreen-BatchDialog-SettingsTextField-Value",
          SELECT: "DynamicScreen-BatchDialog-SettingsSelect",
          EDITORSEARCH: "DynamicScreen-BatchDialog-TreeSelect-EditorTree-TextField-Search",
          EDITORCHECK: "DynamicScreen-BatchDialog-TreeSelect-EditorTree-TreeNodeView-TreeNodeItem-Checkbox",
          BUTTON: "DynamicScreen-Button-Batch",
          SAVE: "DynamicScreen-BatchDialog-TreeSelect-EditorTree-TreePopup-SettingsButton-Save"
        },
        SELECTFILTER: "DynamicScreen-QuestionFilter-SelectField",
        QL: {
          HEADER: {
            NUMBER: "DynamicScreen-QuestionList-TableRenderer-HeaderRenderer-NUMMER",
            SELECT: "DynamicScreen-QuestionList-TableRenderer-HeaderRenderer-SelectionCheckboxHeader"
          },
          BODY: "DynamicScreen-QuestionList-TableRenderer-TableBody",
          ROW: "DynamicScreen-QuestionList-TableRenderer-TableRow",
          EDIT: "DynamicScreen-QuestionList-TableRenderer-BodyRenderer-EditButtonBody",
          SELECT: "DynamicScreen-QuestionList-TableRenderer-BodyRenderer-SelectionCheckboxBody",
          NUMBER: "DynamicScreen-QuestionList-TableRenderer-BodyRenderer-NumberBody",
          STATUS: "DynamicScreen-QuestionList-TableRenderer-BodyRenderer-StatusNameBody",
          LOCKED: "DynamicScreen-QuestionList-TableRenderer-BodyRenderer-LockedStateBody"
        },
        QF: {
          FILTER: {
            STRING: "DynamicScreen-QuestionFilter-StringFilter",
            ENUM: "DynamicScreen-QuestionFilter-EnumFilter-EnumSelect",
            STATUS: "DynamicScreen-QuestionFilter-StatusFilter-EnumSelect",
            TREE: "DynamicScreen-QuestionFilter-TreeFilter-TreeSelect"
          },
          TREE: {
            SEARCH: "DynamicScreen-QuestionFilter-TreeFilter-TreeSelect-TreePopup-TreeSelect-TextField-Search",
            CHECK: "DynamicScreen-QuestionFilter-TreeFilter-TreeSelect-TreePopup-TreeSelect-TreeNodeView-TreeNodeItem-Checkbox",
            SAVE: "DynamicScreen-QuestionFilter-TreeFilter-TreeSelect-TreePopup-TreeSelect-SettingsButton-Save"
          },
          ENUM: {
            SEARCH: "DynamicScreen-QuestionFilter-EnumFilter-EnumSelect-EnumPopup-TextField-Search",
            CHECK: "DynamicScreen-QuestionFilter-EnumFilter-EnumSelect-EnumPopup-EnumView-EnumItem-Checkbox",
            SAVE: "DynamicScreen-QuestionFilter-EnumFilter-EnumSelect-EnumPopup-SettingsButton-Save"
          },
          STATUS: {
            SEARCH: "DynamicScreen-QuestionFilter-StatusFilter-EnumSelect-EnumPopup-TextField-Search",
            CHECK: "DynamicScreen-QuestionFilter-StatusFilter-EnumSelect-EnumPopup-EnumView-EnumItem-Checkbox",
            SAVE: "DynamicScreen-QuestionFilter-StatusFilter-EnumSelect-EnumPopup-SettingsButton-Save"
          }
        }
      }
    },
    LIVE: {
      TREE: "LiveScreen-Tree",
      TREELIST: "LiveScreen-SpeakingRequestsList-DragDropList-Container",
      TAB: {
        OWN: "LiveScreen-Own",
        ALL: "LiveScreen-All",
        WORD: "LiveScreen-Word"
      },
      FOOTER: "LiveScreen-FooterWrapper",
      WM: {
        EDIT: "LiveScreen-StageTreeView-StageTreeViewItem-IconButton-Edit",
        DELETE: "LiveScreen-StageTreeView-StageTreeViewItem-IconButton-Delete",
        CONTAINER: "LiveScreen-StageTreeView-DragDropList-Container",
        SPEAKER: {
          OPTIONS: {
            CY: "LiveScreen-SpeakingRequestContent-SpeakerRequestSpeakerEditor-IconButton",
            ITEM: "LiveScreen-SpeakingRequestContent-SpeakerRequestSpeakerEditor-MenuItem"
          }
        },
        SPEECH: {
          OPTIONS: {
            CY: "LiveScreen-SpeakingRequestsList-IconButton",
            ITEM: "LiveScreen-SpeakingRequestsList-SpeakingRequestMenu-MenuItem"
          }
        },
        STARTSTOP: "LiveScreen-SpeakingRequestContent-SpeakingRequestStartStop-TimerOff-Button-StartStop",
        SRSS: {
          ON: {
            ROOT: "LiveScreen-SpeakingRequestContent-SpeakingRequestStartStop-TimerOn-Container",
            INFO: "LiveScreen-SpeakingRequestContent-SpeakingRequestStartStop-TimerOn-Info",
            MENUBUTTON: "LiveScreen-SpeakingRequestContent-SpeakingRequestStartStop-TimerOn-StartStop"
          },
          OFF: {
            ROOT: "LiveScreen-SpeakingRequestContent-SpeakingRequestStartStop-TimerOff-Container",
            INFO: "LiveScreen-SpeakingRequestContent-SpeakingRequestStartStop-TimerOff-Info",
            MENUBUTTON: "LiveScreen-SpeakingRequestContent-SpeakingRequestStartStop-TimerOff-StartStop"
          }
        },
        CSRD: {
          NAME: "LiveScreen-CreateSpeakingRequestDialog-SettingsTextField-Name",
          SAVE: "LiveScreen-CreateSpeakingRequestDialog-PopupDialog-Button-First"
        },
        ESRD: {
          CANCEL: "LiveScreen-EditSpeakingRequestDialog-PopupDialog-Button-Second",
          SAVE: "LiveScreen-EditSpeakingRequestDialog-PopupDialog-Button-First"
        },
        DSRD: {
          DELETE: "LiveScreen-DeleteSpeakingRequestDialog-PopupDialog-Button-First"
        },
        DSD: {
          DELETE: "LiveScreen-SpeakingRequestContent-SpeakerRequestSpeakerEditor-DeleteSpeakerDialog-PopupDialog-Button-First"
        },
        ESD: {
          NAME: "LiveScreen-SpeakingRequestContent-SpeakerRequestSpeakerEditor-EditSpeakerDialog-SettingsTextField-Name",
          DESC: "LiveScreen-SpeakingRequestContent-SpeakerRequestSpeakerEditor-EditSpeakerDialog-SettingsTextField-Description",
          VIRTUAL: "LiveScreen-SpeakingRequestContent-SpeakerRequestSpeakerEditor-EditSpeakerDialog-SettingsTextField-Virtual",
          CANCEL: "LiveScreen-SpeakingRequestContent-SpeakerRequestSpeakerEditor-EditSpeakerDialog-PopupDialog-Button-Second",
          SALUT: "LiveScreen-SpeakingRequestContent-SpeakerRequestSpeakerEditor-EditSpeakerDialog-SpeakerAttributes-QuestionPropertiesPanelSection-EnumProperty-186a64ed-bf7c-4e39-9ac9-744d2e616611-Select",
          FIRSTNAME: "LiveScreen-SpeakingRequestContent-SpeakerRequestSpeakerEditor-EditSpeakerDialog-SpeakerAttributes-QuestionPropertiesPanelSection-StringProperty-a70e27ef-63c8-4af6-8681-93ffb1199288",
          LASTNAME: "LiveScreen-SpeakingRequestContent-SpeakerRequestSpeakerEditor-EditSpeakerDialog-SpeakerAttributes-QuestionPropertiesPanelSection-StringProperty-9ee8df1d-d3a7-42ee-a457-6c8c70466b4c",
          CITY: "LiveScreen-SpeakingRequestContent-SpeakerRequestSpeakerEditor-EditSpeakerDialog-SpeakerAttributes-QuestionPropertiesPanelSection-StringProperty-6e543ac9-6fe5-485a-9a15-80fbc0f850fa",
          SAVE: "LiveScreen-SpeakingRequestContent-SpeakerRequestSpeakerEditor-EditSpeakerDialog-PopupDialog-Button-First"
        }
      }
    },
    NEWQUESTION: {},
    QUESTIONEDITSCREEN: {
      QES: {
        QPP: {
          SECTION: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection",
          STATUS: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-Status",
          ENUM: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-EnumProperty",
          TREE: {
            PROPERTY: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-TreeProperty",
            RADIO: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-TreeProperty-TreePopup-TreeNodeAlternativeView-TreeNodeAlternativeItem-Radio",
            SAVE: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-TreeProperty-TreePopup-SettingsButton-Save"
          },
          SPEAKER: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-SpeakingRequestProperty-Select",
          STRING: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-StringProperty",
          TAG: {
            CHIP: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-Tags-Chip",
            AUTO: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-Tags-Autocomplete"
          },
          EDITORS: {
            SECTION: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-EditorProperty-Editor",
            NAME: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-EditorProperty-Name",
            TRASH: {
              BUTTON: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-EditorProperty-IconButton-Trash",
              CONFIRM: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-EditorProperty-DeleteQuestionEditorDialog-PopupDialog-Button-First"
            },
            ADD: {
              BUTTON: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-EditorProperty-IconButton-Add",
              FUNCTION: {
                RADIO: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-EditorProperty-AddQuestionEditorDialog-RadioGroup-Function",
              },
              EDITOR: {
                SEARCH: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-EditorProperty-AddQuestionEditorDialog-TreeSelect-TextField-Search",
                RADIO: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-EditorProperty-AddQuestionEditorDialog-TreeSelect-TreeNodeView-TreeNodeItem-Radio"
              },
              CONFIRM: "QuestionEditScreen-QuestionPropertiesPanel-QuestionPropertiesPanelSection-EditorProperty-AddQuestionEditorDialog-PopupDialog-Button-First"
            }
          }
        },
        QE: {
          TOOLBARUNLOCK: "QuestionEditScreen-QuestionEditor-Toolbar-ToolbarTextButton-Unlock-Button",
          TOOLBARLOCK: "QuestionEditScreen-QuestionEditor-Toolbar-ToolbarTextButton-Lock-Button",
          TITLE: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-TitleTextEditor",
          QUESTION: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-RichTextEditor-Question",
          ANSWER: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-RichTextEditor-Answer",
          TEXT1: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-RichTextEditor-Text1",
          TEXT2: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-RichTextEditor-Text2",
          TEXT3: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-RichTextEditor-Text3",
          COMMENTS: {
            SCREEN: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-CommentsView",
            ITEM: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-CommentsView-Item",
            TEXT: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-CommentsView-Text",
            SEND: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-CommentsView-Send",
            DELETE: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-CommentsView-Item-Delete",
            DELETEALL: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-CommentsView-DeleteAll",
            POPUP: {
              DELETE: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-CommentsView-DeleteCommentDialog-PopupDialog-Button-First",
              DELETEALL: "QuestionEditScreen-QuestionEditor-QuestionTextEditor-CommentsView-DeleteAllCommentsDialog-PopupDialog-Button-First"
            }
          }
        },
        QEF: {
          BAR: "QuestionEditScreen-QuestionEditFooter",
          COMMENTS: "QuestionEditScreen-QuestionEditFooter-Button-Comments",
          STATUSBUTTON: "QuestionEditScreen-QuestionEditFooter-QuestionEditFooter-StatusFunctionButtons-StatusFunctionButton",
          NEXT: "QuestionEditScreen-QuestionEditFooter-QuestionEditFooter-StatusFunctionButtons-DefaultNextStatusTransitions",
          POPUP: {
            ERRORTOAST: "QuestionEditScreen-QuestionEditFooter-NextStatusDialog-PopupDialog-Next-Toast",
            PRINTTEMPL: "QuestionEditScreen-QuestionEditFooter-PrintQuestionDialog-RadioGroup",
            PRINT: "QuestionEditScreen-QuestionEditFooter-PrintQuestionDialog-PopupDialog-Button-First",
            RESTORE: "QuestionEditScreen-QuestionEditFooter-RestoreQuestionDialog-PopupDialog-Button-First",
            ARCHIVE: "QuestionEditScreen-QuestionEditFooter-ArchiveQuestionDialog-PopupDialog-Button-First",
            DEARCHIVE: "QuestionEditScreen-QuestionEditFooter-UnArchiveQuestionDialog-PopupDialog-Button-First",
            DELETE: "QuestionEditScreen-QuestionEditFooter-DeleteQuestionDialog-PopupDialog-Button-First",
            NEXT: "QuestionEditScreen-QuestionEditFooter-NextStatusDialog-PopupDialog-Next-Button-First",
            NEXTCANCEL: "QuestionEditScreen-QuestionEditFooter-NextStatusDialog-PopupDialog-Next-Button-Second",
            COPY: "QuestionEditScreen-QuestionEditFooter-CopyQuestionDialog-PopupDialog-Button-First",
            HISTORYDIALOG: "QuestionEditScreen-QuestionEditFooter-QuestionHistoryDialog-PopupDialog-Dialog",
            HISTORYCANCEL: "QuestionEditScreen-QuestionEditFooter-QuestionHistoryDialog-PopupDialog-Button-First",
            RADIOGROUP: "QuestionEditScreen-QuestionEditFooter-NextStatusDialog-RadioGroup",
            TABLE: "QuestionEditScreen-QuestionEditFooter-QuestionHistoryDialog-QuestionHistoryVersionTable-SettingsTableContainer",
            HISTORYRESTORE: "QuestionEditScreen-QuestionEditFooter-QuestionHistoryDialog-QuestionHistoryVersionTable-QuestionHistoryRestoreButton-Restore",
            POPUP: {
              RESTORE: "QuestionEditScreen-QuestionEditFooter-QuestionHistoryDialog-QuestionHistoryVersionTable-QuestionHistoryRestoreDialog-PopupDialog-Button-First"
            }
          }
        },
        SAVESTATE: "QuestionEditScreen-SaveStateWidget"
      },
      QNS: {}
    },
    STAGE: {
      SS: {},
      SDS: {},
      SQAS: {
        NEXT: "StageQaScreen-Next",
        CONTAINER: "StageQaScreen-Tree",
        ALL: "StageQaScreen-All",
        OWN: "StageQaScreen-Own",
        CHATTEXT: "StageQaScreen-ChatWidget-TextField-Text",
        FIRST: "StageQaScreen-QuestionStepper-First",
        LAST: "StageQaScreen-QuestionStepper-Last"
      },
      SSS: {}
    },
    MISSINGPERMISSIONSCREEN: {},
    PRINTTREESCREEN: {
      TABLE: "PrintTreeScreen-PrintSortQuestionList-PrintSortTableRenderer-TableBody",
      ATTRIBUTEBAR: "PrintTreeScreen-AttributeBar",
      ENUMATTRIBUTE: "PrintTreeScreen-EnumField"
    }
  },
  DATAVALUES: {
    GROUPING: {
      ATTRIBUTE: {
        CATEGORY: "d048efdd-2705-4f97-8fb7-513509b0d8bf"
      }
    },
    CHATGROUPS: ["f7cdb2fd-5039-49c5-8331-64ce98a91a9a"]
  },
  POSTFIX: {
    FAIL: "Error",
    PASS: "Success",
    CHAT: "Chat",
    SWITCH: "Button-Chat"
  },
  KEYCLOAK: {
    USERN: "#username",
    PASSW: "#password",
    SUBMIT: "#kc-login"
  },
};
export const DIGIWF = {
  USER: {
  NAME: "test"
}
}

export const DATACY_KEYCLOAK = DATACY.KEYCLOAK;
