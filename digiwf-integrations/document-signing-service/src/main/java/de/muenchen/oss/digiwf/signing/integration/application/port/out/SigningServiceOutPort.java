package de.muenchen.oss.digiwf.signing.integration.application.port.out;

import de.muenchen.oss.digiwf.signing.integration.domain.model.SigningModel;

public interface SigningServiceOutPort {

    SigningModel createSigningUrl();

}
