package de.muenchen.oss.digiwf.signing.integration.adapter.in.streaming;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateSigningEvent {

    @NotBlank
    private String documentPath;

}
