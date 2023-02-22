package io.muenchendigital.digiwf.message.example.process.api;

import io.muenchendigital.digiwf.message.example.process.dto.ProcessMessageDto;
import io.muenchendigital.digiwf.message.example.process.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.example.process.service.ProcessService;
import io.muenchendigital.digiwf.message.process.api.HandleMessagingException;
import io.muenchendigital.digiwf.message.process.api.ProcessApi;
import io.muenchendigital.digiwf.message.process.impl.error.TechnicalError;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProcessController {

    private final ProcessService processService;
    private final ProcessApi processApi;

    @PostMapping("/process/start")
    @HandleMessagingException
    public void startProcess(@RequestBody final StartProcessDto startProcessDto) {
        this.processService.startProcess(startProcessDto);
        try {
            this.processService.startProcess(startProcessDto);
        } catch(final Exception e) {
            this.processApi.handleIncident("", "400", e.getMessage());
        }
    }

    @PostMapping("/process/correlate")
    @HandleMessagingException
    public void correlateMessage(@RequestBody final ProcessMessageDto processMessageDto) throws TechnicalError {
        this.processService.correlateMessage(processMessageDto);
//        try {
//            this.processService.correlateMessage(processMessageDto);
//        }catch (final TechnicalError ex) {
//            this.processApi.handleTechnicalError(ex.getProcessInstanceId(), ex.getErrorCode(), ex.getErrorMessage());
//        } catch(final Exception e) {
//            this.processApi.handleIncident("", "400", e.getMessage());
//        }

    }

}
