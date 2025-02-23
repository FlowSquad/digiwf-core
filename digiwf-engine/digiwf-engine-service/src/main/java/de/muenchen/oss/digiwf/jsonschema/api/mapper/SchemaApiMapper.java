/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.jsonschema.api.mapper;

import com.google.gson.Gson;
import de.muenchen.oss.digiwf.jsonschema.api.transport.JsonSchemaTO;
import de.muenchen.oss.digiwf.jsonschema.domain.model.JsonSchema;
import de.muenchen.oss.digiwf.json.factory.JsonSchemaFactory;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Map between {@link JsonSchemaTO} and {@link }
 *
 * @author externer.dl.horn
 */
@Mapper
public interface SchemaApiMapper {

    JsonSchema map2Model(JsonSchemaTO obj);

    default List<JsonSchemaTO> map2TO(final List<JsonSchema> list) {
        return list.stream().map(this::map2TO).collect(Collectors.toList());
    }

    JsonSchemaTO map2TO(JsonSchema obj);

    default String map(final Map<String, Object> jsonObject) {
        return new Gson().toJson(jsonObject);
    }

    default Map<String, Object> map(final String jsonString) {
        return JsonSchemaFactory.gson().fromJson(jsonString, JsonSchemaFactory.mapType());
    }

}
