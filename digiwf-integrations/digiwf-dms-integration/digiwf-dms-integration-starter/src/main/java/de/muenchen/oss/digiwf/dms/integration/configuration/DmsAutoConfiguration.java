package de.muenchen.oss.digiwf.dms.integration.configuration;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import de.muenchen.oss.digiwf.dms.integration.adapter.in.*;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.auth.DmsUserAdapter;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.auth.MockDmsUserAdapter;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftAdapter;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftClientConfiguration;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft.FabasoftProperties;
import de.muenchen.oss.digiwf.dms.integration.adapter.out.s3.S3Adapter;
import de.muenchen.oss.digiwf.dms.integration.application.port.in.*;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.*;
import de.muenchen.oss.digiwf.dms.integration.application.service.*;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.spring.security.authentication.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
@Import(FabasoftClientConfiguration.class)
@EnableConfigurationProperties({FabasoftProperties.class, DmsProperties.class})
public class DmsAutoConfiguration {

    private final DmsProperties dmsProperties;

    @Bean
    @ConditionalOnMissingBean
    public FabasoftAdapter fabasoftAdapter(final FabasoftProperties dmsProperties, LHMBAI151700GIWSDSoap wsCleint) {
        return new FabasoftAdapter(dmsProperties, wsCleint);
    }

    @Bean
    @ConditionalOnMissingBean
    public S3Adapter s3Adapter(DocumentStorageFileRepository documentStorageFileRepository, DocumentStorageFolderRepository documentStorageFolderRepository) {
        return new S3Adapter(documentStorageFileRepository, documentStorageFolderRepository, dmsProperties.getSupportedExtensions());
    }

    @Bean
    @ConditionalOnMissingBean
    public CreateFileUseCase createFileUseCase(final CreateFilePort createFilePort) {
        return new CreateFileService(createFilePort);
    }

    @Bean
    @ConditionalOnMissingBean
    public CreateProcedureUseCase createProcedureUseCase(final CreateProcedurePort createProcedurePort) {
        return new CreateProcedureService(createProcedurePort);
    }

    @Bean
    @ConditionalOnMissingBean
    public CreateDocumentUseCase createDocumentUseCase(final CreateDocumentPort createDocumentPort, LoadFilePort loadFilePort) {
        return new CreateDocumentService(createDocumentPort, loadFilePort);
    }

    @Bean
    @ConditionalOnMissingBean
    public UpdateDocumentUseCase updateDocumentUseCase(final UpdateDocumentPort updateDocumentPort, LoadFilePort loadFilePort) {
        return new UpdateDocumentService(updateDocumentPort, loadFilePort);
    }

    @Bean
    @ConditionalOnMissingBean
    public DepositObjectUseCase depositObjectUseCase(DepositObjectPort depositObjectPort) {
        return new DepositObjectService(depositObjectPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public CancelObjectUseCase cancelObjectUseCase(CancelObjectPort cancelObjectPort) {
        return new CancelObjectService(cancelObjectPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public ReadContentUseCase readContentUseCase(ReadContentPort readContentPort, TransferContentPort transferContentPort) {
        return new ReadContentService(transferContentPort, readContentPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public SearchFileUseCase searchFileUseCase(SearchFilePort searchFilePort) {
        return new SearchFileService(searchFilePort);
    }

    @Bean
    @ConditionalOnMissingBean
    public SearchSubjectAreaUseCase searchSubjectAreaUseCase(SearchSubjectAreaPort searchSubjectAreaPort) {
        return new SearchSubjectAreaService(searchSubjectAreaPort);
    }

    @Bean
    @ConditionalOnMissingBean
    public ReadMetadataUseCase readMetadataUseCase(ReadMetadataPort readMetadataPort, DmsUserPort dmsUserPort) {
        return new ReadMetadataService(readMetadataPort, dmsUserPort);
    }

    @Bean
    public Consumer<Message<CreateFileDto>> createFile(final MessageProcessor messageProcessor) {
        return messageProcessor.createFile();
    }

    @Bean
    public Consumer<Message<CreateProcedureDto>> createProcedure(final MessageProcessor messageProcessor) {
        return messageProcessor.createProcedure();
    }

    @Bean
    public Consumer<Message<CreateDocumentDto>> createDocument(final MessageProcessor messageProcessor) {
        return messageProcessor.createDocument();
    }

    @Bean
    public Consumer<Message<UpdateDocumentDto>> updateDocument(final MessageProcessor messageProcessor) {
        return messageProcessor.updateDocument();
    }

    @Bean
    public Consumer<Message<DepositObjectDto>> depositObject(final MessageProcessor messageProcessor) {
        return messageProcessor.depositObject();
    }

    @Bean
    public Consumer<Message<CancelObjectDto>> cancelObject(final MessageProcessor messageProcessor) {
        return messageProcessor.cancelObject();
    }

    @Bean
    public Consumer<Message<ReadContentDto>> readContent(final MessageProcessor messageProcessor) {
        return messageProcessor.readContent();
    }

    @Bean
    public Consumer<Message<SearchObjectDto>> searchFile(final MessageProcessor messageProcessor) {
        return messageProcessor.searchFile();
    }

    @Bean
    public Consumer<Message<SearchObjectDto>> searchSubjectArea(final MessageProcessor messageProcessor) {
        return messageProcessor.searchSubjectArea();
    }

    @Profile("!local")
    @Bean
    @ConditionalOnMissingBean
    public DmsUserAdapter dmsUserAdapter (final UserAuthenticationProvider userAuthenticationProvider) {
        return new DmsUserAdapter(userAuthenticationProvider);
    }

    @Profile("local")
    @Bean
    @ConditionalOnMissingBean
    public MockDmsUserAdapter mockDmsUserAdapter () {
        return new MockDmsUserAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageProcessor createMessageProcessor(
            final ProcessApi processApi,
            final ErrorApi errorApi,
            final CreateFileUseCase createFileUseCase,
            final CreateProcedureUseCase createProcedureUseCase,
            final CreateDocumentUseCase createDocumentUseCase,
            final UpdateDocumentUseCase updateDocumentUseCase,
            final DepositObjectUseCase depositObjectUseCase,
            final CancelObjectUseCase cancelObjectUseCase,
            final ReadContentUseCase readContentUseCase,
            final SearchFileUseCase searchFileUseCase,
            final SearchSubjectAreaUseCase searchSubjectAreaUseCase
    ) {
        return new MessageProcessor(
                processApi,
                errorApi,
                createFileUseCase,
                createProcedureUseCase,
                createDocumentUseCase,
                updateDocumentUseCase,
                depositObjectUseCase,
                cancelObjectUseCase,
                readContentUseCase,
                searchFileUseCase,
                searchSubjectAreaUseCase);
    }

}
