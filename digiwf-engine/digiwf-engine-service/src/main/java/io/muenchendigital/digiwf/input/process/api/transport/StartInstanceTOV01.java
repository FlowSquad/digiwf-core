package io.muenchendigital.digiwf.input.process.api.transport;

import lombok.*;
import org.springframework.lang.Nullable;

import java.util.Map;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StartInstanceTOV01 {

    @Nullable
    private String key;

    @Nullable
    private String businessKey;

    @Nullable
    private String fileContext;

    @Nullable
    private String starterOfInstance;

    @Nullable
    private Map<String, Object> data;

}
