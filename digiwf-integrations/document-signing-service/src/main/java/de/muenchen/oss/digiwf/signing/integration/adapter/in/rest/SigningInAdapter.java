package de.muenchen.oss.digiwf.signing.integration.adapter.in.rest;

import de.muenchen.oss.digiwf.signing.integration.application.port.in.CreateSigningInPort;
import de.muenchen.oss.digiwf.signing.integration.domain.model.SigningModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Signing", description = "REST API for signing documents with doXisign")
@RestController
@RequestMapping("/signing")
@RequiredArgsConstructor
public class SigningInAdapter {

    private final CreateSigningInPort createSigningInPort;

    @Operation(
            summary = "Get a signing url for a document",
            description = "Get a signing url for a document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping("/{documentPath}")
    public SigningModel createSigning(@PathVariable final String documentPath) {
        return this.createSigningInPort.createSigning(documentPath);
    }

}
