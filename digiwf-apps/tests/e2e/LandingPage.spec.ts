import TasklistUtils from "../support/TasklistUtils";

describe("DIGIWF - Landing Page Testsuite", (): void => {
  beforeEach(() => {
    cy.login();
    cy.visit("/");
  });

  it("#qsr - open process", () => {
    TasklistUtils.openProcess("AntragDVMoHo");
  });

  it("#taq - open and finish empty process", () => {
    TasklistUtils.openAndFinishEmptyProcess("DigitalWFErleben");
  });

  it("#phk - open task", () => {
    TasklistUtils.openTask();
  });

  it("#klr - open and finish one step task", () => {
    TasklistUtils.openAndFinishOneStepTask();
  });
})
