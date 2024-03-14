package de.muenchen.oss.digiwf.dms.integration.application.service;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.CreateDocumentUseCase;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateDocumentPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.Document;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Validated
@RequiredArgsConstructor
public class CreateDocumentService implements CreateDocumentUseCase {

    private final CreateDocumentPort createDocumentPort;

    private final LoadFilePort loadFilePort;

    @Override
    public String createDocument(
            final String procedureCOO,
            final String title,
            final LocalDate date,
            final String user,
            final DocumentType type,
            final List<String> filepaths,
            final String fileContext
    ) {

        final List<Content> contents = loadFilePort.loadFiles(filepaths, fileContext);

        final Document document = new Document(procedureCOO, title, date, type, contents);

        return createDocumentPort.createDocument(document, user);

    }

}
