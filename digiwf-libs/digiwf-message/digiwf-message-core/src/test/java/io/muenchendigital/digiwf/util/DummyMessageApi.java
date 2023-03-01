package io.muenchendigital.digiwf.util;

import io.muenchendigital.digiwf.message.core.api.MessageApi;

import java.util.Map;

public class DummyMessageApi implements MessageApi {

    @Override
    public boolean sendMessage(final Object payload, final String destination) {
        return true;
    }

    @Override
    public boolean sendMessage(final Object payload, final Map<String, Object> headers, final String destination) {
        return true;
    }
}
