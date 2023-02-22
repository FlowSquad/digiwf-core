package io.muenchendigital.digiwf.message.example.api;

import io.muenchendigital.digiwf.message.example.model.Message;
import io.muenchendigital.digiwf.message.example.model.MessageSuccess;
import io.muenchendigital.digiwf.message.example.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/message")
    public MessageSuccess sendMessage(final Message msg) {
        return this.messageService.sendMessage(msg);
    }

}
