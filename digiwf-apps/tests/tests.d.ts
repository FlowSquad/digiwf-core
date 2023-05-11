declare namespace Cypress {
  // noinspection JSUnusedGlobalSymbols
  interface Chainable {
    drawer(item: string): void
    login(user?: string, pw?: string): Cypress.Chainable
  }
}
