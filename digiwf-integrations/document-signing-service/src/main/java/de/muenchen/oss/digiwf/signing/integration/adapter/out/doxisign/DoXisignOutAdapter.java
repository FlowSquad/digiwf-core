package de.muenchen.oss.digiwf.signing.integration.adapter.out.doxisign;

import de.muenchen.oss.digiwf.signing.integration.application.port.out.SigningServiceOutPort;
import de.muenchen.oss.digiwf.signing.integration.domain.model.SigningModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoXisignOutAdapter implements SigningServiceOutPort {

    private final DoXisignConfigurationProperties doXisignConfigurationProperties;

    @Override
    public SigningModel createSigningUrl() {
        final String host = this.doXisignConfigurationProperties.getHost();
        return SigningModel.builder()
                .signingHost(host)
                .signingUrl(String.format("%s/doxiview", host))
                .build();
    }
}
