declare namespace Cypress {
  // noinspection JSUnusedGlobalSymbols
  interface Chainable {
    login(url: string, user?: string, pw?: string): Cypress.Chainable
  }
}
