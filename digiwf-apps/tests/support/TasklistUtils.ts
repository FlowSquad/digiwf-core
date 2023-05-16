import {
  DATACY_DRAWER as DRAWER,
  DATACY_INSTANCES as INSTANCES,
  DATACY_PROCESSES as PROCESSES,
  DATACY_TASKS as TASKS,
  MSG
} from "./constants";
import {simpleArrayCompare} from "./jscommands";

const getCurrentInstances = (): Cypress.Chainable => {
  let currentInstances: Array<string> = [];
  cy.drawer(DRAWER.INSTANCES);
  return cy.get(`[data-cy=${INSTANCES.LIST.ROOT}] > div > div > div`).then(($lines: JQuery<HTMLElement>): Cypress.Chainable => {
    if (!$lines.text().includes(MSG[0])) {
      cy.get(`[data-cy^=${INSTANCES.LIST.ITEM}]`).each(($instance: JQuery<HTMLElement>): void => {
        if (currentInstances.indexOf($instance.attr("data-cy")) === -1) {
          currentInstances.push($instance.attr("data-cy"));
        }
      });
    }
    return cy.wrap(currentInstances);
  });
}

class TasklistUtils {
  static processList(): Cypress.Chainable {
    return cy.get(`[data-cy=${PROCESSES}]`);
  }

  static startProcess(name: string): Cypress.Chainable {
    return cy.get(`[data-cy=${PROCESSES.ITEM}-${name}]`);
  }

  /**
   * openProcess
   *
   * @param {string} name - name of process
   */
  static openProcess(name: string): void {
    cy.drawer(DRAWER.PROCESSES);
    cy.get(`[data-cy=${PROCESSES.ITEM}-${name}]`).click();
    cy.get(`[data-cy=${PROCESSES.HEADLINE}]`).should("be.visible");
  }

  /**
   * openAndFinishEmptyProcess
   *
   * @param {string} name - name of process
   */
  static openAndFinishEmptyProcess(name: string): void {
    let currentInstancesBefore: Array<string> = [];
    getCurrentInstances().then((currentInstances: Array<string>): void => {
      currentInstancesBefore = currentInstances;
    });
    cy.drawer(DRAWER.PROCESSES);
    cy.get(`[data-cy=${PROCESSES.ITEM}-${name}]`).click();
    cy.get(`[data-cy=${PROCESSES.HEADLINE}]`).should("be.visible");
    cy.get("button").last().click();
    // guard: page changed
    cy.get(`[data-cy=${PROCESSES.HEADLINE}]`).should("not.exist");
    getCurrentInstances().then((currentInstances: Array<string>): void => {
      expect(simpleArrayCompare(currentInstancesBefore, currentInstances)).to.false;
    });
  }

  /**
   * openTask
   */
  static openTask(index = 0): void {
    cy.drawer(DRAWER.TASKS);
    cy.get(`[data-cy=${TASKS.LIST.ROOT}]`).should("contain.text", "Aufgaben gefunden");
    //cy.get(`[data-cy=tasklist] div:nth-child(${index}) > a`).click();
  }
}

export default TasklistUtils;
