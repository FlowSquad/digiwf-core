/*
 * TasklistUtils
 * Encapsulates cypress functionality for testing the digiwf landing page.
 * @author: Martin Berner
 */
import {
  DRAWERS
} from "./constants";
import {simpleArrayCompare} from "./jscommands";

const getCurrentListItems = (drawer): Cypress.Chainable => {
  let currentInstances: Array<string> = [];
  cy.drawer(drawer.DRAWER);
  return cy.get(`[data-cy=${drawer.LIST.ROOT}]`).then(($list: JQuery<HTMLElement>): Cypress.Chainable => {
    if (!$list.text().includes(drawer.LIST.MSG)) {
      cy.get(`[data-cy^=${drawer.LIST.ITEM}]`).each(($instance: JQuery<HTMLElement>): void => {
        if (currentInstances.indexOf($instance.attr("data-cy")) === -1) {
          currentInstances.push($instance.attr("data-cy"));
        }
      });
    }
    return cy.wrap(currentInstances);
  });
}

class TasklistUtils {
  /**
   * openProcess
   * (tests for the headline of the opened process)
   *
   * @param {string} name - name of process
   */
  static openProcess(name: string): void {
    cy.drawer(DRAWERS.PROCESSES.DRAWER);
    cy.get(`[data-cy=${DRAWERS.PROCESSES.LIST.ROOT}]`).should("not.contain.text", DRAWERS.PROCESSES.LIST.MSG);
    cy.get(`[data-cy=${DRAWERS.PROCESSES.LIST.ITEM}-${name}]`).click();
    cy.get(`[data-cy=${DRAWERS.PROCESSES.LIST.ITEM_HEADLINE}]`).should("be.visible");
  }
F
  /**
   * openAndFinishEmptyProcess
   * (tests for a new item has been inserted in the first page of the instances list)
   *
   * @param {string} name - name of process
   * @requires {} - new items are inserted at the top of the list
   */
  static openAndFinishEmptyProcess(name: string): void {
    let currentInstancesBefore: Array<string> = [];
    getCurrentListItems(DRAWERS.INSTANCES).then((currentInstances: Array<string>): void => {
      currentInstancesBefore = currentInstances;
    });
    cy.drawer(DRAWERS.PROCESSES.DRAWER);
    cy.get(`[data-cy=${DRAWERS.PROCESSES.LIST.ITEM}-${name}]`).click();
    cy.get(`[data-cy=${DRAWERS.PROCESSES.LIST.ITEM_HEADLINE}]`).should("be.visible");
    cy.get("form button").last().click();
    // guard: page changed
    cy.get(`[data-cy=${DRAWERS.PROCESSES.LIST.ITEM_HEADLINE}]`).should("not.exist");
    getCurrentListItems(DRAWERS.INSTANCES).then((currentInstances: Array<string>): void => {
      expect(simpleArrayCompare(currentInstancesBefore, currentInstances)).to.false;
    });
  }

  /**
   * openTask
   * (tests for the headline of the opened task)
   *
   * @param {number} index - position of task item in list
   */
  static openTask(index = 0): void {
    cy.drawer(DRAWERS.TASKS.DRAWER);
    cy.get(`[data-cy=${DRAWERS.TASKS.LIST.ROOT}]`).should("not.contain.text", DRAWERS.TASKS.LIST.MSG);
    cy.get(`[data-cy=${DRAWERS.TASKS.LIST.ROOT}] div > a`).eq(index).click();
    cy.get(`[data-cy=${DRAWERS.TASKS.LIST.ITEM_HEADLINE}]`).should("be.visible");
  }

  /**
   * openAndFinishTask
   * (tests for a new item has been inserted/removed in the first page of the tasks list)
   *
   * @param {number} index - position of task item in list
   * @requires {} - new items are inserted at the top of the list, task could be finish in one step
   */
  static openAndFinishOneStepTask(index = 0): void {
    let currentTasksBefore: Array<string> = [];
    getCurrentListItems(DRAWERS.TASKS).then((currentInstances: Array<string>): void => {
      currentTasksBefore = currentInstances;
    });
    cy.drawer(DRAWERS.TASKS.DRAWER);
    cy.get(`[data-cy=${DRAWERS.TASKS.LIST.ROOT}] div > a`).eq(index).click();
    cy.get(`[data-cy=${DRAWERS.TASKS.LIST.ITEM_HEADLINE}]`).should("be.visible");
    cy.get("form button").last().click();
    // guard: page changed
    cy.get(`[data-cy=${DRAWERS.TASKS.LIST.ITEM_HEADLINE}]`).should("not.exist");
    getCurrentListItems(DRAWERS.TASKS).then((currentInstances: Array<string>): void => {
      expect(simpleArrayCompare(currentTasksBefore, currentInstances)).to.false;
    });
  }
}

export default TasklistUtils;

// it.skip("#agt - start process - begin contract", () => {
//   cy.intercept("GET", API.BACKEND_SERVICE.FILTER).as("getFilter");
//   // first: get existing instances
//   let currentInstances: Array<string> = [];
//   let currentInstancesAfter: Array<string> = [];
//   cy.drawer(DRAWER.INSTANCES);
//   cy.get(`[data-cy=${INSTANCES.LIST.ROOT}] > div`).then(($lines: JQuery<HTMLElement>): void => {
//       if(!$lines.text().includes(MSG[0])) {
//         cy.get(`[data-cy^=${INSTANCES.LIST.ITEM}]`).each(($instance: JQuery<HTMLElement>): void => {
//           cy.wrap($instance, {log: false}).invoke({log: false}, "attr", "data-cy").then(($datacy: string): void => {
//             if (currentInstances.indexOf($datacy) === -1) {
//               currentInstances.push($datacy);
//             }
//           });
//         });
//       }
//   });
//   // begin process: formular
//   cy.drawer(DRAWER.PROCESSES);
//   TasklistUtils.startProcess(PROCESSES.BEGIN_CONTRACT).click();
//   cy.get("form input[type=text]").eq(0).click();
//   cy.get("div[role=option]").contains("POR").click();
//   cy.get("form input[type=text]").eq(1).as("boss");
//   cy.get("@boss").click();
//   cy.get("@boss").type("John");
//   cy.get("div[role=listbox]").contains("John Doe").click();
//   cy.get("form input[type=date]").eq(0).as("startDate");
//   cy.get("@startDate").click();
//   cy.get("@startDate").type("2023-05-10");
//   cy.get("form input[type=text]").eq(2).click();
//   cy.get("div[role=listbox]").contains("unbefristet").click();
//   cy.get("form input[type=date]").eq(1).as("endDate");
//   cy.get("@endDate").click();
//   cy.get("@endDate").type("2023-05-21");
//   cy.get("form input[type=number]").eq(0).as("homeOffice");
//   cy.get("@homeOffice").click();
//   cy.get("@homeOffice").clear();
//   cy.get("@homeOffice").type("10");
//   cy.get("form input[type=number]").eq(1).as("mobileOffice");
//   cy.get("@mobileOffice").click();
//   cy.get("@mobileOffice").clear();
//   cy.get("@mobileOffice").type("5");
//   cy.get("form input[type=text]").eq(3).as("service");
//   cy.get("@service").click();
//   cy.get("@service").type("Mo/9:00");
//   cy.get("form input[type=text]").eq(4).as("address");
//   cy.get("@address").click();
//   cy.get("@address").type("Schiller Str. 3");
//   cy.get("form input[type=text]").eq(5).click();
//   cy.get("div[role=listbox]").contains("mit einem bereits").click();
//   cy.get("form textarea").click();
//   cy.get("form textarea").type("Nur ein Test");
//   cy.get("form input[type=checkbox]").eq(0).check({force: true});
//   cy.get("form input[type=checkbox]").eq(1).check({force: true});
//   cy.get("form input[type=checkbox]").eq(2).check({force: true});
//   //
//   cy.get("button").contains("Abschliessen").click();
//   // guard: save action finished
//   cy.get(`[data-cy=${PROCESSES.SEARCH_FIELD}]`).should("be.visible");
//   // doublecheck: new instance
//   cy.drawer(DRAWER.INSTANCES);
//   cy.get(`[data-cy=${INSTANCES.LIST.ROOT}] > div`).then(($lines: JQuery<HTMLElement>): void => {
//     if(!$lines.text().includes(MSG[0])) {
//       cy.get(`[data-cy^=${INSTANCES.LIST.ITEM}]`).each(($instance: JQuery<HTMLElement>): void => {
//         cy.wrap($instance, {log: false}).invoke({log: false}, "attr", "data-cy").then(($datacy: string): void => {
//           if (currentInstancesAfter.indexOf($datacy) === -1) {
//             currentInstancesAfter.push($datacy);
//           }
//         });
//       });
//     }
//   }).then((): void => {
//     expect(simpleArrayCompare(currentInstances, currentInstancesAfter)).to.false;
//   });
// });
//
// it.skip("#xgt - start process - change contract", () => {
//   cy.intercept("GET", API.BACKEND_SERVICE.FILTER).as("getFilter");
//   // first: get existing instances
//   let currentInstances: Array<string> = [];
//   let currentInstancesAfter: Array<string> = [];
//   cy.drawer(DRAWER.INSTANCES);
//   cy.get(`[data-cy=${INSTANCES.LIST.ROOT}] > div`).then(($lines: JQuery<HTMLElement>): void => {
//     if(!$lines.text().includes(MSG[0])) {
//       cy.get(`[data-cy^=${INSTANCES.LIST.ITEM}]`).each(($instance: JQuery<HTMLElement>): void => {
//         cy.wrap($instance, {log: false}).invoke({log: false}, "attr", "data-cy").then(($datacy: string): void => {
//           if (currentInstances.indexOf($datacy) === -1) {
//             currentInstances.push($datacy);
//           }
//         });
//       });
//     }
//   });
//   // start process: formular
//   cy.drawer(DRAWER.PROCESSES);
//   TasklistUtils.startProcess(PROCESSES.CHANGE_CONTRACT).click();
//   cy.get("form input[type=checkbox]").eq(0).check({force: true});
//   cy.get("form input[type=checkbox]").eq(1).check({force: true});
//   cy.get("form input[type=checkbox]").eq(2).check({force: true});
//   cy.get("form input[type=checkbox]").eq(3).check({force: true});
//   cy.get("form input[type=text]").eq(0).click();
//   cy.get("div[role=listbox]").contains("POR").click();
//   cy.get("form input[type=text]").eq(1).as("boss");
//   cy.get("@boss").click();
//   cy.get("@boss").type("John");
//   cy.get("div[role=listbox]").contains("John Doe").click();
//   cy.get("form input[type=date]").eq(0).as("startDate");
//   cy.get("@startDate").click();
//   cy.get("@startDate").type("2023-05-10");
//   cy.get("form input[type=text]").eq(2).click();
//   cy.get("div[role=listbox]").contains("unbefristet").click();
//   cy.get("form input[type=date]").eq(1).as("endDate");
//   cy.get("@endDate").click();
//   cy.get("@endDate").type("2023-05-21");
//   cy.get("form input[type=number]").eq(0).as("homeOffice");
//   cy.get("@homeOffice").click();
//   cy.get("@homeOffice").clear();
//   cy.get("@homeOffice").type("10");
//   cy.get("form input[type=number]").eq(1).as("mobileOffice");
//   cy.get("@mobileOffice").click();
//   cy.get("@mobileOffice").clear();
//   cy.get("@mobileOffice").type("5");
//   cy.get("form input[type=text]").eq(3).as("service");
//   cy.get("@service").click();
//   cy.get("@service").type("Mo/9:00");
//   cy.get("form input[type=text]").eq(4).as("address");
//   cy.get("@address").click();
//   cy.get("@address").type("Schiller Str. 3");
//   cy.get("form input[type=text]").eq(5).click();
//   cy.get("div[role=listbox]").contains("mit einem bereits").click();
//   cy.get("form textarea").click();
//   cy.get("form textarea").type("Nur ein Test");
//   cy.get("form button").contains("Abschliessen").click();
//   // guard: save action finished
//   cy.get(`[data-cy=${PROCESSES.SEARCH_FIELD}]`).should("be.visible");
//   // doublecheck: new instance
//   cy.drawer(DRAWER.INSTANCES);
//   cy.get(`[data-cy=${INSTANCES.LIST.ROOT}] > div`).then(($lines: JQuery<HTMLElement>): void => {
//     if(!$lines.text().includes(MSG[0])) {
//       cy.get(`[data-cy^=${INSTANCES.LIST.ITEM}]`).each(($instance: JQuery<HTMLElement>): void => {
//         cy.wrap($instance, {log: false}).invoke({log: false}, "attr", "data-cy").then(($datacy: string): void => {
//           if (currentInstancesAfter.indexOf($datacy) === -1) {
//             currentInstancesAfter.push($datacy);
//           }
//         });
//       });
//     }
//   }).then((): void => {
//     expect(simpleArrayCompare(currentInstances, currentInstancesAfter)).to.false;
//   });
// });
