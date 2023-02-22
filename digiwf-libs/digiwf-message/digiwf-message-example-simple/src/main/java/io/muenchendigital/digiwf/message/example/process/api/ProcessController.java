package io.muenchendigital.digiwf.message.example.process.api;

import io.muenchendigital.digiwf.message.example.process.dto.ProcessMessageDto;
import io.muenchendigital.digiwf.message.example.process.dto.StartProcessDto;
import io.muenchendigital.digiwf.message.example.process.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProcessController {

    private final ProcessService processService;

    @PostMapping("/process/start")
    public void startProcess(@RequestBody final StartProcessDto startProcessDto) {
        this.processService.startProcess(startProcessDto);
    }

    @PostMapping("/process/correlate")
    public void correlateMessage(@RequestBody final ProcessMessageDto processMessageDto) {
        this.processService.correlateMessage(processMessageDto);

    }

}
