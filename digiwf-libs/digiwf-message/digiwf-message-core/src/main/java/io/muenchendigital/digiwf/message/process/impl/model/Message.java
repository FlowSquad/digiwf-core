package io.muenchendigital.digiwf.message.process.impl.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Message class with headers and payload.
 *
 * @param <T> the messages payload type
 */
@Getter
public class Message<T> {
    private final Map<String, Object> headers;
    private T payload;

    public Message() {
        this.headers = new HashMap<>();
    }

    public void addHeader(final String key, final String value) {
        this.headers.put(key, value);
    }

    public void addPayload(final T payload) {
        this.payload = payload;
    }
}
