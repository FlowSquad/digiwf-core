import {
  DATACY_KEYCLOAK as KC
} from "./constants";

Cypress.Commands.add("login", (url: string, user = "johndoe", pw = Cypress.env("KEYCLOAK_ADMIN")): Cypress.Chainable => {
  return cy.session([url, user, pw], () => {
    const credentials = {username: user, password: pw};
    // blank page
    // get login form
    cy.visit("/");
    cy.get(KC.USERN).should("be.visible"); // wait for form loaded
    // type credentials
    cy.get(KC.USERN).type(credentials.username);
    cy.get(KC.PASSW).type(credentials.password, {log: false});
    // submit form
    cy.pause();
    //cy.get(KC.SUBMIT).click();
    cy.get("#kc-form-login").submit();
    cy.pause();
     // cy.origin("http://localhost:8082", {args: credentials}, () => {

      // cy.visit("/");
       // cy.url().should("include", `${Cypress.config("baseUrl")}`);
      // type credentials
      // cy.get(KC.USERN).type(credentials.username);
      // cy.get(KC.PASSW).type(credentials.password, {log: false});
      // submit form
      // cy.get(KC.SUBMIT).click();
      // cy.request("GET", "http://keycloak:8080/auth/realms/P82").then((response) => {
      // cy.log(response.body)
   // });
    //  });
    // cy.getAllCookies().then(cookies => {
    //     cy.log(JSON.stringify(cookies));
    // });
    // receive the token
    // get redirected
    // cy.url().should("include", `${Cypress.config("baseUrl")}/#`);
    // session created

    // cy.url().should("include", `${Cypress.config("baseUrl")}`);

    //cy.wait(3000);
    //cy.visit("/");
    // cy.log("WEWRWREWR");
  });
});
