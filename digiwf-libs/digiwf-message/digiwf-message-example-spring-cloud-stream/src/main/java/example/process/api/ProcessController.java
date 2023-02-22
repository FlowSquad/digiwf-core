package example.process.api;


import example.process.dto.StartProcessDto;
import example.process.service.ProcessService;
import io.muenchendigital.digiwf.message.process.impl.dto.CorrelateMessageDto;
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
    public void correlateMessage(@RequestBody final CorrelateMessageDto processMessageDto) {
        this.processService.correlateMessage(processMessageDto);

    }

}
