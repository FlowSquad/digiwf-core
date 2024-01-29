package de.muenchen.oss.digiwf.dms.integration.adapter.in;

import de.muenchen.oss.digiwf.dms.integration.application.port.in.*;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import org.mockito.Mockito;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

import static de.muenchen.oss.digiwf.message.common.MessageConstants.*;

class MessageProcessorTestBase {
    protected final ErrorApi errorApiMock = Mockito.mock(ErrorApi.class);
    protected final ProcessApi processApi = Mockito.mock(ProcessApi.class);
    protected final CreateFileUseCase createFileUseCaseMock = Mockito.mock(CreateFileUseCase.class);
    protected final CreateProcedureUseCase createProcedureMock = Mockito.mock(CreateProcedureUseCase.class);
    protected final CreateDocumentUseCase createDocumentUseCaseMock = Mockito.mock(CreateDocumentUseCase.class);
    protected final UpdateDocumentUseCase updateDocumentUseCaseMock = Mockito.mock(UpdateDocumentUseCase.class);
    protected final DepositObjectUseCase depositObjectUseCaseMock = Mockito.mock(DepositObjectUseCase.class);
    protected final CancelObjectUseCase cancelObjectUseCaseMock = Mockito.mock(CancelObjectUseCase.class);
    protected final ReadContentUseCase readContentUseCase = Mockito.mock(ReadContentUseCase.class);
    protected final SearchFileUseCase searchFileUseCase = Mockito.mock(SearchFileUseCase.class);
    protected final SearchSubjectAreaUseCase searchSubjectAreaUseCase = Mockito.mock(SearchSubjectAreaUseCase.class);
    protected final String processInstanceId = "exampleProcessInstanceId";
    protected final MessageHeaders messageHeaders = new MessageHeaders(Map.of(DIGIWF_PROCESS_INSTANCE_ID, this.processInstanceId, DIGIWF_INTEGRATION_NAME, "dmsIntegration", TYPE, "type"));
    protected MessageProcessor messageProcessor;

    protected void setupBase() {
        this.messageProcessor = new MessageProcessor(
                processApi,
                errorApiMock,
                createFileUseCaseMock,
                createProcedureMock,
                createDocumentUseCaseMock,
                updateDocumentUseCaseMock,
                depositObjectUseCaseMock,
                cancelObjectUseCaseMock,
                readContentUseCase,
                searchFileUseCase,
                searchSubjectAreaUseCase);
    }
}

