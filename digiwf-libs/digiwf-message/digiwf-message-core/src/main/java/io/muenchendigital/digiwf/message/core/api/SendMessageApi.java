package io.muenchendigital.digiwf.message.core.api;

public interface SendMessageApi {

    boolean sendMessage(Object payload, String destination);

}
