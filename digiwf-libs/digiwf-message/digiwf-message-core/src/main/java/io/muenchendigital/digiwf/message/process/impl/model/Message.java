package io.muenchendigital.digiwf.message.process.impl.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Message<T> {
    private final Map<String, String> headers;
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
