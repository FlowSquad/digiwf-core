import {DATACY_TASKS as TASKS} from "./constants";

class Tasks {
  static taskList(): Cypress.Chainable {
    return cy.get(`[data-cy=${TASKS.LIST}]`);
  }

  static openTask(id): Cypress.Chainable {
    return cy.get(`[data-cy=${id}]`);
  }
}

export default Tasks;
