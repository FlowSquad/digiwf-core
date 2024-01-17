package de.muenchen.oss.digiwf.signing.integration.application.port.in;

import de.muenchen.oss.digiwf.signing.integration.domain.model.SigningModel;

public interface CreateSigningInPort {

    SigningModel createSigning(String documentPath);

}
