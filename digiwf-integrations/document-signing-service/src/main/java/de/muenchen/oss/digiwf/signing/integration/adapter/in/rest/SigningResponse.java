package de.muenchen.oss.digiwf.signing.integration.adapter.in.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SigningResponse {
        private String signingUrl;
}
