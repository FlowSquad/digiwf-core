package io.muenchendigital.digiwf.integration.adapter.shared;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants for the headers used in the spring cloud messages.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StreamingHeaders {

    public static final String TYPE = "type";
    public static final String DIGIWF_MESSAGE_NAME = "digiwf.messagename";
    public static final String DIGIWF_PROCESS_INSTANCE_ID = "digiwf.processinstanceid";
}
