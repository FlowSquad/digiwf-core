package io.muenchendigital.digiwf.message.example.service;

import io.muenchendigital.digiwf.message.core.api.SendMessageApi;
import io.muenchendigital.digiwf.message.example.model.Message;
import io.muenchendigital.digiwf.message.example.model.MessageSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SendMessageApi sendMessageApi;

    // in this use case we don't really need the destination
    private final String messageDestination = "my-destination";

    public MessageSuccess sendMessage(final Message message) {
        final boolean success = this.sendMessageApi.sendMessage(message, this.messageDestination);
            return new MessageSuccess(success, success ? "Message was successfully sent": "Sending message failed");
    }

}
