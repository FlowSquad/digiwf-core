package de.muenchen.oss.digiwf.task.service.adapter.out.schema;

import de.muenchen.oss.digiwf.task.service.application.port.out.schema.JsonSchemaValidationPort;
import de.muenchen.oss.digiwf.task.service.domain.JsonSchema;
import io.holunda.polyflow.view.Task;
import de.muenchen.oss.digiwf.json.serialization.JsonSerializationService;
import de.muenchen.oss.digiwf.json.validation.DigiWFValidationException;
import de.muenchen.oss.digiwf.json.validation.JsonSchemaValidator;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonSchemaValidationAdapter implements JsonSchemaValidationPort {
    private final JsonSerializationService serializationService;
    private final JsonSchemaValidator jsonSchemaValidator;
    private final EngineDataMapper engineDataMapper;

    @Override
    public Map<String, Object> validateAndSerialize(JsonSchema schema, Task task, Map<String, Object> variables) throws DigiWFValidationException {

        val filteredData = this.serializationService.filter(schema.asMap(), variables, true);

        this.jsonSchemaValidator.validate(schema.asMap(), filteredData.toMap());

        val taskData = this.engineDataMapper.mapToData(task.getPayload());
        val targetData = this.serializationService.deserializeData(schema.asMap(), taskData);
        val serializedData = this.serializationService.merge(filteredData, new JSONObject(targetData));

        return this.engineDataMapper.mapObjectsToVariables(serializedData);
    }

    @Override
    public Map<String, Object> filterVariables(Map<String, Object> data, JsonSchema schema) {
        return this.engineDataMapper.mapToData(this.serializationService.deserializeData(schema.asMap(), data));
    }
}
