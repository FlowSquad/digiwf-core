import {
  DATACY_KEYCLOAK  as KC
} from "./constants";
Cypress.Commands.add("login", (url: string, user = "johndoe", pw = Cypress.env("KEYCLOAK_ADMIN")): Cypress.Chainable => {
    return cy.session([url, user, pw], () => {
        // blank page
        // get login form
        cy.visit(url);
        cy.get(KC.USERN).should("be.visible"); // wait for form loaded
        // type credentials
        cy.get(KC.USERN).type(user);
        cy.get(KC.PASSW).type(pw);
        // submit form
        cy.get(KC.SUBMIT).click();
        // receive the token
        // get redirected
        // cy.url().should("include", `${Cypress.config("baseUrl")}/#`);
        // session created
        cy.pause();
    });
});
