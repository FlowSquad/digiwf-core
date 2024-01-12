package de.muenchen.oss.digiwf.signing.integration.application.port.in;

import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;
import de.muenchen.oss.digiwf.signing.integration.domain.model.CreateSigning;

public interface CreateSigningInPort {

    CreateSigning createSigning(String documentPath) throws DocumentStorageException, PropertyNotSetException, DocumentStorageClientErrorException, DocumentStorageServerErrorException;

}
