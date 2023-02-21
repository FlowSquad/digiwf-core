package io.muenchendigital.digiwf.message.process.impl.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class StartProcessDto {
    private String key;
    private String fileContext;
    private Map<String, Object> data;
}
