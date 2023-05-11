import {DATACY_PROCESSES as PROCESSES} from "./constants";

class Processes {
  static taskList(): Cypress.Chainable {
    return cy.get(`[data-cy=${PROCESSES}]`);
  }

  static openTask(id): Cypress.Chainable {
    return cy.get(`[data-cy=${id}]`);
  }
}

export default Processes;
