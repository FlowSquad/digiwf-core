package de.muenchen.oss.digiwf.dms.integration.application.port.in;

import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import jakarta.validation.constraints.NotBlank;

public interface CreateProcedureUseCase {

    Procedure createProcedure(@NotBlank final String titel, @NotBlank final String fileCOO, final String fileSubj, @NotBlank final String user);

}
