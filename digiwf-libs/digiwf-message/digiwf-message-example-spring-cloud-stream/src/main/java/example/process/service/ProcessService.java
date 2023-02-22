package example.process.service;

import example.process.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProcessService {

    private final ProcessApi processApi;

    public void startProcess(final StartProcessDto startProcessDto) {
        this.processApi.startProcess(startProcessDto.getKey(), startProcessDto.getVariables());
    }

    public void correlateMessage(final CorrelateMessageDto messageDto) {
        this.processApi.correlateMessage(messageDto.getProcessInstanceId(), messageDto.getMessageName(), messageDto.getPayloadVariables());
    }

}
