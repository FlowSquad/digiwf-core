package io.muenchendigital.digiwf.message.example.process.service;

import io.muenchendigital.digiwf.message.example.process.dto.ProcessMessageDto;
import io.muenchendigital.digiwf.message.example.process.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.impl.error.TechnicalError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProcessService {

    private final ProcessApi processApi;

    public void startProcess(final StartProcessDto startProcessDto) throws RuntimeException {
        if (startProcessDto.getKey() == null || startProcessDto.getKey().isEmpty()) {
            // incident occurred
            throw new RuntimeException("Can not start process. No process key defined.");
        }
        this.processApi.startProcess(startProcessDto.getKey(), startProcessDto.getVariables());
    }

    public void correlateMessage(final ProcessMessageDto messageDto) throws RuntimeException {
        if (messageDto.getProcessInstanceId() == null || messageDto.getProcessInstanceId().isEmpty()) {
            // incident occurred
            throw new RuntimeException("Can not correlate message. ProcessInstanceId is missing.");
        }
        if (messageDto.getVariables().isEmpty()) {
            throw new TechnicalError(messageDto.getProcessInstanceId(), "400", "No variables defined.");
        }
        this.processApi.correlateMessage(messageDto.getProcessInstanceId(), messageDto.getMessageName(), messageDto.getVariables());
    }

}
