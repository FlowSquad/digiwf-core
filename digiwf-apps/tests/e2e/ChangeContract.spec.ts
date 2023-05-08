import {
  DIGIWF
} from "../support/constants";

describe("DIGIWF - Change Contract Testsuite", () => {
  before(() => {
    //
  });
  beforeEach(() => {
    cy.login("/");
    //cy.origin("http://keycloak:8080/auth/realms/P82", () => {
     // cy.visit("/");
    // cy.request("http://keycloak:8080/auth/realms/P82");
    // cy.request("http://localhost:8082/api/gateway/session");
    //});
  // cy.pause();
    // cy.contains("span", "Vorgang Starten");
  });

  it("#agt - create", () => {
    cy.pause();
    // cy.visit("/#/process");
    cy.log("WEWRWREWR");
  });
})
