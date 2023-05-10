declare namespace Cypress {
  // noinspection JSUnusedGlobalSymbols
  interface Chainable {
    login(user?: string, pw?: string): Cypress.Chainable
  }
}
