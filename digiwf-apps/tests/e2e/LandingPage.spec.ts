import TasklistUtils from "../support/TasklistUtils";
import {API, DATACY_DRAWER as DRAWER, DATACY_PROCESSES as PROCESSES, DATACY_INSTANCES as INSTANCES, MSG} from "../support/constants";
import {simpleArrayCompare} from "../support/jscommands";

describe("DIGIWF - Landing Page Testsuite", (): void => {
  beforeEach(() => {
    cy.login();
    cy.visit("/");
  });

  it("#qsr - open process", () => {
    TasklistUtils.openProcess("AntragDVMoHo");
  });

  it("#taq - open and finish empty process", () => {
    TasklistUtils.openAndFinishEmptyProcess("DigitalWFErleben");
  });

  it.skip("#phk - open task", () => {
    TasklistUtils.openTask(2);
  });

  it.skip("#klr - open and end task", () => {

  });

  it.skip("#agt - start process - begin contract", () => {
    cy.intercept("GET", API.BACKEND_SERVICE.FILTER).as("getFilter");
    // first: get existing instances
    let currentInstances: Array<string> = [];
    let currentInstancesAfter: Array<string> = [];
    cy.drawer(DRAWER.INSTANCES);
    cy.get(`[data-cy=${INSTANCES.LIST.ROOT}] > div`).then(($lines: JQuery<HTMLElement>): void => {
        if(!$lines.text().includes(MSG[0])) {
          cy.get(`[data-cy^=${INSTANCES.LIST.ITEM}]`).each(($instance: JQuery<HTMLElement>): void => {
            cy.wrap($instance, {log: false}).invoke({log: false}, "attr", "data-cy").then(($datacy: string): void => {
              if (currentInstances.indexOf($datacy) === -1) {
                currentInstances.push($datacy);
              }
            });
          });
        }
    });
    // begin process: formular
    cy.drawer(DRAWER.PROCESSES);
    TasklistUtils.startProcess(PROCESSES.BEGIN_CONTRACT).click();
    cy.get("form input[type=text]").eq(0).click();
    cy.get("div[role=option]").contains("POR").click();
    cy.get("form input[type=text]").eq(1).as("boss");
    cy.get("@boss").click();
    cy.get("@boss").type("John");
    cy.get("div[role=listbox]").contains("John Doe").click();
    cy.get("form input[type=date]").eq(0).as("startDate");
    cy.get("@startDate").click();
    cy.get("@startDate").type("2023-05-10");
    cy.get("form input[type=text]").eq(2).click();
    cy.get("div[role=listbox]").contains("unbefristet").click();
    cy.get("form input[type=date]").eq(1).as("endDate");
    cy.get("@endDate").click();
    cy.get("@endDate").type("2023-05-21");
    cy.get("form input[type=number]").eq(0).as("homeOffice");
    cy.get("@homeOffice").click();
    cy.get("@homeOffice").clear();
    cy.get("@homeOffice").type("10");
    cy.get("form input[type=number]").eq(1).as("mobileOffice");
    cy.get("@mobileOffice").click();
    cy.get("@mobileOffice").clear();
    cy.get("@mobileOffice").type("5");
    cy.get("form input[type=text]").eq(3).as("service");
    cy.get("@service").click();
    cy.get("@service").type("Mo/9:00");
    cy.get("form input[type=text]").eq(4).as("address");
    cy.get("@address").click();
    cy.get("@address").type("Schiller Str. 3");
    cy.get("form input[type=text]").eq(5).click();
    cy.get("div[role=listbox]").contains("mit einem bereits").click();
    cy.get("form textarea").click();
    cy.get("form textarea").type("Nur ein Test");
    cy.get("form input[type=checkbox]").eq(0).check({force: true});
    cy.get("form input[type=checkbox]").eq(1).check({force: true});
    cy.get("form input[type=checkbox]").eq(2).check({force: true});
    //
    cy.get("button").contains("Abschliessen").click();
    // guard: save action finished
    cy.get(`[data-cy=${PROCESSES.SEARCH_FIELD}]`).should("be.visible");
    // doublecheck: new instance
    cy.drawer(DRAWER.INSTANCES);
    cy.get(`[data-cy=${INSTANCES.LIST.ROOT}] > div`).then(($lines: JQuery<HTMLElement>): void => {
      if(!$lines.text().includes(MSG[0])) {
        cy.get(`[data-cy^=${INSTANCES.LIST.ITEM}]`).each(($instance: JQuery<HTMLElement>): void => {
          cy.wrap($instance, {log: false}).invoke({log: false}, "attr", "data-cy").then(($datacy: string): void => {
            if (currentInstancesAfter.indexOf($datacy) === -1) {
              currentInstancesAfter.push($datacy);
            }
          });
        });
      }
    }).then((): void => {
      expect(simpleArrayCompare(currentInstances, currentInstancesAfter)).to.false;
    });
  });

  it.skip("#xgt - start process - change contract", () => {
    cy.intercept("GET", API.BACKEND_SERVICE.FILTER).as("getFilter");
    // first: get existing instances
    let currentInstances: Array<string> = [];
    let currentInstancesAfter: Array<string> = [];
    cy.drawer(DRAWER.INSTANCES);
    cy.get(`[data-cy=${INSTANCES.LIST.ROOT}] > div`).then(($lines: JQuery<HTMLElement>): void => {
      if(!$lines.text().includes(MSG[0])) {
        cy.get(`[data-cy^=${INSTANCES.LIST.ITEM}]`).each(($instance: JQuery<HTMLElement>): void => {
          cy.wrap($instance, {log: false}).invoke({log: false}, "attr", "data-cy").then(($datacy: string): void => {
            if (currentInstances.indexOf($datacy) === -1) {
              currentInstances.push($datacy);
            }
          });
        });
      }
    });
    // start process: formular
    cy.drawer(DRAWER.PROCESSES);
    TasklistUtils.startProcess(PROCESSES.CHANGE_CONTRACT).click();
    cy.get("form input[type=checkbox]").eq(0).check({force: true});
    cy.get("form input[type=checkbox]").eq(1).check({force: true});
    cy.get("form input[type=checkbox]").eq(2).check({force: true});
    cy.get("form input[type=checkbox]").eq(3).check({force: true});
    cy.get("form input[type=text]").eq(0).click();
    cy.get("div[role=listbox]").contains("POR").click();
    cy.get("form input[type=text]").eq(1).as("boss");
    cy.get("@boss").click();
    cy.get("@boss").type("John");
    cy.get("div[role=listbox]").contains("John Doe").click();
    cy.get("form input[type=date]").eq(0).as("startDate");
    cy.get("@startDate").click();
    cy.get("@startDate").type("2023-05-10");
    cy.get("form input[type=text]").eq(2).click();
    cy.get("div[role=listbox]").contains("unbefristet").click();
    cy.get("form input[type=date]").eq(1).as("endDate");
    cy.get("@endDate").click();
    cy.get("@endDate").type("2023-05-21");
    cy.get("form input[type=number]").eq(0).as("homeOffice");
    cy.get("@homeOffice").click();
    cy.get("@homeOffice").clear();
    cy.get("@homeOffice").type("10");
    cy.get("form input[type=number]").eq(1).as("mobileOffice");
    cy.get("@mobileOffice").click();
    cy.get("@mobileOffice").clear();
    cy.get("@mobileOffice").type("5");
    cy.get("form input[type=text]").eq(3).as("service");
    cy.get("@service").click();
    cy.get("@service").type("Mo/9:00");
    cy.get("form input[type=text]").eq(4).as("address");
    cy.get("@address").click();
    cy.get("@address").type("Schiller Str. 3");
    cy.get("form input[type=text]").eq(5).click();
    cy.get("div[role=listbox]").contains("mit einem bereits").click();
    cy.get("form textarea").click();
    cy.get("form textarea").type("Nur ein Test");
    cy.get("form button").contains("Abschliessen").click();
    // guard: save action finished
    cy.get(`[data-cy=${PROCESSES.SEARCH_FIELD}]`).should("be.visible");
    // doublecheck: new instance
    cy.drawer(DRAWER.INSTANCES);
    cy.get(`[data-cy=${INSTANCES.LIST.ROOT}] > div`).then(($lines: JQuery<HTMLElement>): void => {
      if(!$lines.text().includes(MSG[0])) {
        cy.get(`[data-cy^=${INSTANCES.LIST.ITEM}]`).each(($instance: JQuery<HTMLElement>): void => {
          cy.wrap($instance, {log: false}).invoke({log: false}, "attr", "data-cy").then(($datacy: string): void => {
            if (currentInstancesAfter.indexOf($datacy) === -1) {
              currentInstancesAfter.push($datacy);
            }
          });
        });
      }
    }).then((): void => {
      expect(simpleArrayCompare(currentInstances, currentInstancesAfter)).to.false;
    });
  });
})
