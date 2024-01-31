package de.muenchen.oss.digiwf.cosys.integration.application.port.out;

public interface GenerateWebformUrlPort {

    String generateWebformUrl(String webformGuid, String role, String client);

}
