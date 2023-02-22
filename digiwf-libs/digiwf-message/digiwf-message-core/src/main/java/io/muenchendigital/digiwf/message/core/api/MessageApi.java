package io.muenchendigital.digiwf.message.core.api;

import java.util.Map;

public interface MessageApi {

    boolean sendMessage(Object payload, String destination);

    boolean sendMessage(Object payload, Map<String, Object> headers, String destination);

}
