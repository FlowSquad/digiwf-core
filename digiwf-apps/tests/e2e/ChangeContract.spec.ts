import Processes from "../support/Processes";
import {DATACY_DRAWER as DRAWER} from "../support/constants";

describe("DIGIWF - Change Contract Testsuite", (): void => {
  beforeEach(() => {
    cy.login();
    cy.visit("/");
  });

  it("#agt - verify change contract formular", () => {
    cy.drawer(DRAWER.PROCESSES);
    cy.log("Dummy Test");
  });

  it("#agt - verify change contract formular", () => {
    cy.drawer(DRAWER.INSTANCES);
    cy.log("Dummy Test 6");
  });
})
