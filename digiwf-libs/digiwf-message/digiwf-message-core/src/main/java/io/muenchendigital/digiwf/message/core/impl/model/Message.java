package io.muenchendigital.digiwf.message.core.impl.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Message {
    private final Map<String, Object> headers;
    private Object payload;

    public Message() {
        this.headers = new HashMap<>();
    }

    public void addHeader(final String key, final String value) {
        this.headers.put(key, value);
    }

    public void addPayload(final Object payload) {
        this.payload = payload;
    }
}
