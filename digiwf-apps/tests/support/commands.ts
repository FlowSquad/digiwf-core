import {
  KEYCLOAK as KC
} from "./constants";

Cypress.Commands.add("login", (user = Cypress.env('auth2_username'), pw = Cypress.env('auth2_password')): Cypress.Chainable => {
  return cy.session([user, pw], (): void => {
    Cypress.log({
      displayName: 'AUTH2 LOGIN',
      message: [`🔐 Authenticating | ${user}`]
    })
    cy.visit("/"); // becomes redirected to keycloak login for
    cy.get(KC.USERN).type(user);
    cy.get(KC.PASSW).type(pw, {log: false});
    cy.get(KC.SUBMIT).click();
    // guard: page changed
    cy.url().should('contain', 'mytask')
  });
});

Cypress.Commands.add("drawer", (item): void => {
  cy.get(`[data-cy=${item}]`).click();
  // guard: page changed
  cy.get("#suchfeld").should("be.visible");
});

