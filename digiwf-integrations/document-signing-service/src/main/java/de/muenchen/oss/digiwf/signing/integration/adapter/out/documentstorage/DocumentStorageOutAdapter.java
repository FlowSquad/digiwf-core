package de.muenchen.oss.digiwf.signing.integration.adapter.out.documentstorage;

import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.s3.integration.client.repository.presignedurl.PresignedUrlRepository;
import de.muenchen.oss.digiwf.signing.integration.application.port.out.DocumentStorageOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DocumentStorageOutAdapter implements DocumentStorageOutPort {

    private final PresignedUrlRepository presignedUrlRepository;

    @Override
    public String createStorageUrl(String documentPath) throws DocumentStorageException, PropertyNotSetException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        return presignedUrlRepository.getPresignedUrlUpdateFile(documentPath, 20160, null);
    }

}
