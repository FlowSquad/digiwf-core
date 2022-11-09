package io.muenchendigital.digiwf.shared.file;

import io.muenchendigital.digiwf.service.config.process.ProcessConfigFunctions;
import io.muenchendigital.digiwf.shared.file.presignedUrlAdapters.PresignedUrlAction;
import io.muenchendigital.digiwf.shared.file.presignedUrlAdapters.PresignedUrlAdapter;
import io.muenchendigital.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * General logic for handling file storage.
 *
 * @author martin.dietrich
 */
@Slf4j
public abstract class AbstractFileService {

    public static final String FILEPATH_DELIMITER = ";";
    public static final String ERRTEXT_ILLEGAL_ACCESS = "No access to defined property";

    protected final DocumentStorageFolderRepository documentStorageFolderRepository;
    private final List<PresignedUrlAdapter> presignedUrlAdapters;
    private final ProcessConfigFunctions processConfigFunctions;

    public AbstractFileService(
            final DocumentStorageFolderRepository documentStorageFolderRepository,
            final List<PresignedUrlAdapter> presignedUrlAdapters,
            final ProcessConfigFunctions processConfigFunctions
    ) {
        this.documentStorageFolderRepository = documentStorageFolderRepository;
        this.presignedUrlAdapters = presignedUrlAdapters;
        this.processConfigFunctions = processConfigFunctions;
    }

    public List<String> getFileNames(final String filePath, final String fileContext, final Optional<String> documentStorageUrl) {
        try {
            if (documentStorageUrl.isPresent()) {
                return this.removeFolderFromPaths(this.documentStorageFolderRepository.getAllFilesInFolderRecursively(fileContext + "/" + filePath, documentStorageUrl.get()));
            }
            return this.removeFolderFromPaths(this.documentStorageFolderRepository.getAllFilesInFolderRecursively(fileContext + "/" + filePath));
        } catch (final Exception ex) {
            log.error("Getting all files of folder {} failed: {}", filePath, ex);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Getting all files of folder %s failed", filePath));
        }
    }

    protected String getPresignedUrl(final PresignedUrlAction action, final String pathToFile, final Optional<String> documentStorageUrl) {
        final Optional<PresignedUrlAdapter> handler = this.presignedUrlAdapters.stream()
                .filter(h -> h.isResponsibleForAction(action))
                .findAny();
        if (handler.isPresent()) {
            if (documentStorageUrl.isPresent()) {
                return handler.get().getPresignedUrl(documentStorageUrl.get(), pathToFile, 5);
            }
            return handler.get().getPresignedUrl(pathToFile, 5);
        }
        log.warn("No handler specified for action {}", action);
        throw new RuntimeException(String.format("No handler specified for action %s", action));
    }

    protected Optional<String> getDocumentStorageUrl(final String definitionKey) {
        return this.processConfigFunctions.get("app_file_s3_sync_config", definitionKey);
    }

    //---------------------------------------- helper methods ---------------------------------------- //

    private List<String> removeFolderFromPaths(final List<String> fileList) {
        return fileList.stream()
                .map(file -> file.substring(file.lastIndexOf("/") + 1))
                .collect(Collectors.toList());
    }

}
