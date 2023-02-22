package example.process.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessMessageDto {
    private String processInstanceId;
    private String messageName;
    private String businessKey;
    private Map<String, Object> payloadVariables;
    private Map<String, Object> payloadVariablesLocal;
}
