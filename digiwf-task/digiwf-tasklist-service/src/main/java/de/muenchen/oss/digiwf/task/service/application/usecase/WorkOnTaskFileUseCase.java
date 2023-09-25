package de.muenchen.oss.digiwf.task.service.application.usecase;

import io.holunda.polyflow.view.Task;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.task.service.application.port.in.WorkOnTaskFile;
import de.muenchen.oss.digiwf.task.service.application.port.out.auth.CurrentUserPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.file.PresignedUrlPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.file.TaskFileConfigResolverPort;
import de.muenchen.oss.digiwf.task.service.application.port.out.polyflow.TaskQueryPort;
import de.muenchen.oss.digiwf.task.service.domain.PresignedUrlAction;
import de.muenchen.oss.digiwf.task.service.domain.TaskFileConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkOnTaskFileUseCase implements WorkOnTaskFile {

    protected final DocumentStorageFolderRepository documentStorageFolderRepository;

    private final PresignedUrlPort presignedUrlPort;

    private final TaskFileConfigResolverPort taskFileConfigResolverPort;

    private final TaskQueryPort taskQueryPort;

    private final CurrentUserPort currentUserPort;

    private TaskFileConfig fileConfig;

    @NonNull
    public List<String> getFileNames(@NonNull final String taskId, @NonNull final String filePath) {

        this.initializeFileConfig(taskId);
        this.fileConfig.checkReadAccess(filePath);

        final String fileContext = this.fileConfig.processFileContext;

        try {
            String documentStorageUrl = this.fileConfig.processSyncConfig;
            String pathToFolder = fileContext + "/" + filePath;
            if (documentStorageUrl != null) {
                return this.removeFolderFromPaths(this.documentStorageFolderRepository.getAllFilesInFolderRecursively(pathToFolder, documentStorageUrl).block());
            }
            return this.removeFolderFromPaths(this.documentStorageFolderRepository.getAllFilesInFolderRecursively(pathToFolder).block());
        } catch (final Exception ex) {
            log.error("Getting all files of folder {} failed", filePath, ex);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Getting all files of folder %s failed", filePath));
        }
    }

    @Override
    @NonNull
    public String getPresignedUrl(final PresignedUrlAction action, @NonNull final String taskId, @NonNull final String filePath, @NonNull final String fileName) {

        this.initializeFileConfig(taskId);

        if (action.equals(PresignedUrlAction.GET)) {
            this.fileConfig.checkReadAccess(filePath);
        } else {
            this.fileConfig.checkWriteAccess(filePath);
        }

        final String fileContext = this.fileConfig.processFileContext;

        String documentStorageUrl = this.fileConfig.processSyncConfig;
        String pathToFile = fileContext + "/" + filePath + "/" + fileName;

        if (documentStorageUrl != null) {
            return presignedUrlPort.getPresignedUrl(documentStorageUrl, pathToFile, 5, action);
        }
        return presignedUrlPort.getPresignedUrl(pathToFile, 5, action);
    }

    private List<String> removeFolderFromPaths(final Set<String> fileList) {
        return fileList.stream()
                .map(file -> file.substring(file.lastIndexOf("/") + 1))
                .collect(Collectors.toList());
    }

    private void initializeFileConfig(String taskId) {
        Task task = getTaskForUser(taskId);
        this.fileConfig = taskFileConfigResolverPort.apply(task);
    }

    private Task getTaskForUser(String taskId) {
        val currentUser = currentUserPort.getCurrentUser();
        return taskQueryPort.getTaskByIdForCurrentUser(currentUser, taskId);
    }



}
