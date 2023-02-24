package io.muenchendigital.digiwf.message.core.impl.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a message with headers and a payload.
 */
@Getter
public class Message {
    private final Map<String, Object> headers;
    private Object payload;

    public Message() {
        this.headers = new HashMap<>();
    }

    /**
     * Adds a header to the message with the specified key-value pair.
     * @param key The header key.
     * @param value The header value.
     */
    public void addHeader(final String key, final String value) {
        this.headers.put(key, value);
    }

    /**
     * Sets the payload of the message.
     * @param payload The payload object to be included in the message.
     */
    public void addPayload(final Object payload) {
        this.payload = payload;
    }
}
