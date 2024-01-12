package de.muenchen.oss.digiwf.signing.integration.application.port.out;

import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageClientErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.DocumentStorageServerErrorException;
import de.muenchen.oss.digiwf.s3.integration.client.exception.PropertyNotSetException;

public interface DocumentStorageOutPort {

    String createStorageUrl(String documentPath) throws DocumentStorageException, PropertyNotSetException, DocumentStorageClientErrorException, DocumentStorageServerErrorException;

}
