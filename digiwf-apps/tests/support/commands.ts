import {
  DATACY_TASKS as TASKS,
  DATACY_KEYCLOAK as KC,
  API as API
} from "./constants";

Cypress.Commands.add("login", (user = Cypress.env('auth2_username'), pw = Cypress.env('auth2_password')): Cypress.Chainable => {
  return cy.session([user, pw], (): void => {
    const testIds: Record<string, string> = { list: TASKS.LIST.ROOT };
    Cypress.log({
      displayName: 'AUTH2 LOGIN',
      message: [`🔐 Authenticating | ${user}`]
    })
    cy.intercept("GET", API.BACKEND_SERVICE.TASKS).as("getTasks");
    cy.intercept("GET", API.BACKEND_SERVICE.FILTER).as("getFilter");
    cy.visit("/"); // becomes redirected to keycloak login form
    cy.get(KC.USERN).type(user);
    cy.get(KC.PASSW).type(pw, { log: false });
    cy.get(KC.SUBMIT).click();
    // NOTE: Currently required workaround (cross-origin)
    cy.origin(Cypress.config("baseUrl"), {args: testIds}, (testIds: Record<string, string>): void => {
      cy.get("table a").click();
      cy.get(`[data-cy=${testIds.list}] > div`).should("have.length.greaterThan", 3); // wait for the page loaded
      cy.wait(["@getTasks", "@getFilter"]); // wait for the login to complete
    });
  });
});
