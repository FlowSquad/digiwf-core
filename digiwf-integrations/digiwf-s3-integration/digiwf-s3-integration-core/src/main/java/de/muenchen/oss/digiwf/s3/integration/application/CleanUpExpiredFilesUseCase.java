package de.muenchen.oss.digiwf.s3.integration.application;

import de.muenchen.oss.digiwf.s3.integration.application.port.in.CleanUpExpiredFilesInPort;
import de.muenchen.oss.digiwf.s3.integration.adapter.out.persistence.File;
import de.muenchen.oss.digiwf.s3.integration.adapter.out.persistence.FileRepository;
import de.muenchen.oss.digiwf.s3.integration.adapter.out.s3.S3Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CleanUpExpiredFilesUseCase implements CleanUpExpiredFilesInPort {

  private final FileRepository fileRepository;
  private final S3Repository s3Repository;

  /**
   * Cronjob scheduled method which deletes all folders in the S3 storage and database
   * for which the {@link File#getEndOfLife()} attribute is exceeded.
   */
  @Override
  public void cleanUpExpiredFolders() {
    log.info("S3 and database clean up for expired files started.");
    this.fileRepository.findAllByEndOfLifeNotNullAndEndOfLifeBefore(LocalDate.now())
        .forEach(this::deleteFile);
    log.info("S3 and database clean up for expired files finished.");
  }

  private void deleteFile(final File file) {
    try {
      String path = file.getPathToFile();
      // Delete file on S3
      this.s3Repository.deleteFile(path);
      // Delete database entry
      this.fileRepository.deleteByPathToFile(path);
    } catch (final Exception exception) {
      log.error("Error during cleanup happened.", exception);
    }
  }
}
