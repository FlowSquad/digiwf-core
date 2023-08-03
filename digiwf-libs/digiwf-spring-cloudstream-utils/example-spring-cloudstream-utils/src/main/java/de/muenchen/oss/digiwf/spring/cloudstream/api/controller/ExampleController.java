package de.muenchen.oss.digiwf.spring.cloudstream.api.controller;

import de.muenchen.oss.digiwf.spring.cloudstream.utils.api.streaming.service.PayloadSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@Deprecated
public class ExampleController {

    private final PayloadSenderService genericPayloadSenderService;

    @PostMapping(value = "/sendMessage")
    @Deprecated
    public void sendMessage(@RequestBody @NotNull final String payload,
                            @RequestParam(defaultValue = "processMessage") final String type) {
        this.genericPayloadSenderService.sendPayload(payload, type);
    }

}
