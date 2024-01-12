package de.muenchen.oss.digiwf.signing.integration.application.port;

import de.muenchen.oss.digiwf.signing.integration.application.port.in.CreateSigningInPort;
import de.muenchen.oss.digiwf.signing.integration.application.port.out.SigningServiceOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateSigningUseCase implements CreateSigningInPort {

    private final SigningServiceOutPort signingServiceOutPort;

    @Override
    public String createSigning(String documentPath) {
        return signingServiceOutPort.createSigningUrl();
    }
}
