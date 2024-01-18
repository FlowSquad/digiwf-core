package de.muenchen.oss.digiwf.signing.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SigningModel {
    private String signingHost;
    private String signingUrl;
}
