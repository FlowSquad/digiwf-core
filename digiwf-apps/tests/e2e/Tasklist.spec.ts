import {TasklistUtils} from "../support/TasklistUtils";

describe("DIGIWF - Tasklist Testsuite", (): void => {
  beforeEach(() => {
    cy.login();
    cy.visit("/");
  });

  it("#qsr - open process", () => {
    TasklistUtils.openProcess("AntragDVMoHo");
  });

  it("#taq - open and start empty process", () => {
    TasklistUtils.openAndStartProcess("DigitalWFErleben");
  });

  it("#phk - open task", () => {
    TasklistUtils.openTask();
  });

  it("#klr - open and finish one step task", () => {
    TasklistUtils.openAndFinishOneStepTask();
  });

  // TODO: is in work, not finished yet
  it.skip("#gbl - open and finish process", () => {
    TasklistUtils.openAndStartProcess("Usertask-Example", "tests/fixtures/data_3424.json");
  });
})
