package de.muenchen.oss.digiwf.cosys.integration.application.port.in;

public interface CreateWebformUrlInPort {

    String createWebformUrl(final String webformGuid, final String role, final String client);

}
