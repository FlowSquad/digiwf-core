package de.muenchen.oss.digiwf.cosys.integration.adapter.out;

import de.muenchen.oss.digiwf.cosys.integration.api.GenerationApi;
import de.muenchen.oss.digiwf.cosys.integration.api.WebformApi;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.GenerateDocumentPort;
import de.muenchen.oss.digiwf.cosys.integration.application.port.out.GenerateWebformUrlPort;
import de.muenchen.oss.digiwf.cosys.integration.configuration.CosysConfiguration;
import de.muenchen.oss.digiwf.cosys.integration.model.GenerateDocument;
import de.muenchen.oss.digiwf.cosys.integration.model.Generator;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static de.muenchen.oss.digiwf.cosys.integration.adapter.out.FileUtils.createFile;

@Slf4j
@RequiredArgsConstructor
public class CosysAdapter implements GenerateDocumentPort, GenerateWebformUrlPort {

    public static final String DATA_FILE_NAME = "data";
    public static final String MERGE_FILE_NAME = "merge";

    private final CosysConfiguration configuration;
    private final GenerationApi generationApi;

    private final WebformApi webformApi;


    /**
     * Generate a Document in Cosys
     *
     * @param generateDocument Data for generating documents
     * @return the generated document
     */
    @Override
    public Mono<byte[]> generateCosysDocument(final GenerateDocument generateDocument) {
        try {
            return this.generationApi.generatePdfWithResponseSpec(
                            generateDocument.getGuid(),
                            generateDocument.getClient(),
                            generateDocument.getRole(),
                            createFile(DATA_FILE_NAME, generateDocument.getVariables().toString().getBytes(StandardCharsets.UTF_8)),
                            generateDocument.getOutputFormat(),
                            null,
                            null,
                            null,
                            null,
                            false,
                            createFile(MERGE_FILE_NAME, this.configuration.getMergeOptions()),
                            null,
                            null
                    )
                    .onStatus(HttpStatusCode::is5xxServerError,
                            response -> response.bodyToMono(byte[].class).flatMap(body -> Mono.error(new IncidentError("Document could not be created."))))
                    .onStatus(HttpStatusCode::is4xxClientError,
                            response -> response.bodyToMono(byte[].class).flatMap(body -> Mono.error(new BpmnError("COSYS_DOCUMENT_CREATION_FAILED", "Document could not be created."))))
                    .bodyToMono(byte[].class);

        } catch (final IOException ex) {
            log.error("Document could not be created.", ex);
            throw new BpmnError("COSYS_DOCUMENT_CREATION_FAILED", "Document could not be created.");
        }
    }

    @Override
    public String generateWebformUrl(String webformGuid, String role, String client) {
        final Generator generator = new Generator();
        generator.setType("de.cib.cosys.rest.DynamicJsGenerator");
        List<String> headIncludes = List.of(
                "<script src=\"../../webjars/webform/postmessage.js\"></script>",
                "<script src=\"../../webjars/webform/controls.js\"></script>",
                "<script src=\"../../webjars/webform/messages.js\"></script>",
                "<link rel=\"stylesheet\" href=\"../../../webform/integration.css\"></link>");
        generator.headIncludes(headIncludes);
        val response = this.webformApi.prepareWebform(webformGuid, client, role, generator, null, null).block();
        return this.configuration.getWebformUrl() + response.getFormId() + "/html";
    }
}
