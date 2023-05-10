import {
  DATACY_KEYCLOAK as KC
} from "./constants";

Cypress.Commands.add("login", (user = Cypress.env('auth2_username'), pw = Cypress.env('auth2_password')): Cypress.Chainable => {
  return cy.session([user, pw], (): void => {
    const log = Cypress.log({
      displayName: 'AUTH2 LOGIN',
      message: [`🔐 Authenticating | ${user}`],
      autoEnd: false
    })
    log.snapshot('before');
    cy.intercept("GET", "/api/digitalwf-backend-service/rest/task/group/open*").as("getTasks");
    cy.intercept("GET", "/api/digitalwf-backend-service/rest/filter").as("getFilter");

    cy.visit("/"); // becomes redirected to keycloak login form
    cy.get(KC.USERN).type(user);
    cy.get(KC.PASSW).type(pw, { log: false });
    cy.get(KC.SUBMIT).click();
    // NOTE: Currently required workaround (cross-origin)
    cy.origin("http://localhost:8082", (): void => {
      cy.get("table a").click();
      cy.wait(4000); // wait for corresponding GET requests being sent
      cy.wait(["@getTasks", "@getFilter"]); // wait for the login to complete
    });

    log.snapshot('after');
    log.end();
  });
});
