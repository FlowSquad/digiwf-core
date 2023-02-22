package example.process.service;

import example.process.dto.ProcessMessageDto;
import example.process.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProcessService {

    private final ProcessApi processApi;

    public void startProcess(final StartProcessDto startProcessDto) {
        if (startProcessDto.getKey() == null || startProcessDto.getKey().isEmpty()) {
            this.processApi.handleIncident("", "400", "Can not start process. No process key defined.");
        }
        this.processApi.startProcess(startProcessDto.getKey(), startProcessDto.getVariables());
    }

    public void correlateMessage(final ProcessMessageDto messageDto) {
        if (messageDto.getProcessInstanceId() == null || messageDto.getProcessInstanceId().isEmpty()) {
            this.processApi.handleIncident("", "400", "Can not correlate message. ProcessInstanceId is missing.");
        }
        if (messageDto.getVariables().isEmpty()) {
            this.processApi.handleTechnicalError(messageDto.getProcessInstanceId(), "400", "No variables defined.");
        }
        this.processApi.correlateMessage(messageDto.getProcessInstanceId(), messageDto.getMessageName(), messageDto.getVariables());
    }

}
