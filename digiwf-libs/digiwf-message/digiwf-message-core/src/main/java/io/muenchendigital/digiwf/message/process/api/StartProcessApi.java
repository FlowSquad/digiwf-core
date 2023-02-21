package io.muenchendigital.digiwf.message.process.api;

import java.util.Map;

public interface StartProcessApi {

    boolean startProcess(String processKey, Map<String, Object> variables);
    boolean startProcess(String processKey, Map<String, Object> variables, String fileContext);

}
