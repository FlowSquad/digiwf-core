package de.muenchen.oss.digiwf.signing.integration.adapter.out.doxisign;

import de.muenchen.oss.digiwf.signing.integration.application.port.out.SigningServiceOutPort;
import org.springframework.stereotype.Component;

@Component
public class DoXisignOutAdapter implements SigningServiceOutPort {

    @Override
    public String createSigningUrl() {
        // TODO proper implement me
        return "https://doxiview.com/showcase/?locale=de#de&feature=sign";
    }
}
