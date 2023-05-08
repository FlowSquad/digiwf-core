import {
  DIGIWF
} from "../support/constants";

describe("DIGIWF - Change Contract Testsuite", () => {
  before(() => {
    //
  });
  beforeEach(() => {
    cy.login("/");
  });

  it("#agt - create", () => {
    cy.log("WERWREWREWREWEWREW")
    cy.visit("/#/mytask");
    // cy.origin("http://localhost:8082", () => {
    //   cy.visit("/#/mytask");
    //   // cy.log("WEWRWREWR");
    cy.wait(120000);
    cy.pause();
    // });
  });
})
