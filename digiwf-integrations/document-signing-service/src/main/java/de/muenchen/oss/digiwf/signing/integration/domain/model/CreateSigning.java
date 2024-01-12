package de.muenchen.oss.digiwf.signing.integration.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateSigning {

    private String storageUrl;
    private String signingUrl;

}
