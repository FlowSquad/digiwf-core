package de.muenchen.oss.digiwf.signing.integration.application;

import de.muenchen.oss.digiwf.signing.integration.application.port.in.CreateSigningInPort;
import de.muenchen.oss.digiwf.signing.integration.application.port.out.SigningServiceOutPort;
import de.muenchen.oss.digiwf.signing.integration.domain.model.SigningModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateSigningUseCase implements CreateSigningInPort {

    private final SigningServiceOutPort signingServiceOutPort;

    @Override
    public SigningModel createSigning(String documentPath) {
        return signingServiceOutPort.createSigningUrl();
    }
}
