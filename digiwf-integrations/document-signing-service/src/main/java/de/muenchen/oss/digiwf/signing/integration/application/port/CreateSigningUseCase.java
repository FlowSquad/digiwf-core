package de.muenchen.oss.digiwf.signing.integration.application.port;

import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.signing.integration.application.port.in.CreateSigningInPort;
import de.muenchen.oss.digiwf.signing.integration.application.port.out.DocumentStorageOutPort;
import de.muenchen.oss.digiwf.signing.integration.application.port.out.SigningServiceOutPort;
import de.muenchen.oss.digiwf.signing.integration.domain.model.CreateSigning;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateSigningUseCase implements CreateSigningInPort {

    private final DocumentStorageOutPort documentStorageOutPort;
    private final SigningServiceOutPort signingServiceOutPort;

    @Override
    public CreateSigning createSigning(String documentPath) throws DocumentStorageException, PropertyNotSetException, DocumentStorageClientErrorException, DocumentStorageServerErrorException {
        final String storageUrl = documentStorageOutPort.createStorageUrl(documentPath);
        final String signingUrl = signingServiceOutPort.createSigningUrl();
        return CreateSigning.builder()
                .storageUrl(storageUrl)
                .signingUrl(signingUrl)
                .build();
    }
}
