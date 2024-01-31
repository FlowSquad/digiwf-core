package de.muenchen.oss.digiwf.cosys.integration.application.usecase;

import de.muenchen.oss.digiwf.cosys.integration.application.port.in.CreateWebformUrlInPort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.GenerateWebformUrlPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateWebformUrlUseCase implements CreateWebformUrlInPort {

    private final GenerateWebformUrlPort generateWebformUrlPort;

    @Override
    public String createWebformUrl(String webformGuid, String role, String client) {
        //return generateWebformUrlPort.generateWebformUrl(webformGuid, role, client);
        return "https://cosys4lhm-dev.cib.de/webtom/webform/test2143/html";
    }
}
