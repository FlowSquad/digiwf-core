package io.muenchendigital.digiwf.message.core.impl;

import io.muenchendigital.digiwf.message.core.impl.model.Message;

public interface SendMessagePort {

    boolean sendMessage(Message message, String destination);

}
