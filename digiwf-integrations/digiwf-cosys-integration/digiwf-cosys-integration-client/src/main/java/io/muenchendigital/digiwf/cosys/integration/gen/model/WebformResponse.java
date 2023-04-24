/*
 * cosys-eai
 * This application provides the routing for calls from Fachanwendungen to coSys.
 *
 * The version of the OpenAPI document: 2.1.5-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.muenchendigital.digiwf.cosys.integration.gen.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * WebformResponse
 */
@JsonPropertyOrder({
  WebformResponse.JSON_PROPERTY_FORM_ID,
  WebformResponse.JSON_PROPERTY_FIELDS,
  WebformResponse.JSON_PROPERTY_EVENTS
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class WebformResponse {
  public static final String JSON_PROPERTY_FORM_ID = "formId";
  private String formId;

  public static final String JSON_PROPERTY_FIELDS = "fields";
  private Map<String, String> fields = new HashMap<>();

  public static final String JSON_PROPERTY_EVENTS = "events";
  private Map<String, List<String>> events = new HashMap<>();

  public WebformResponse() {
  }

  public WebformResponse formId(String formId) {
    
    this.formId = formId;
    return this;
  }

   /**
   * The formId of the prepared webform.
   * @return formId
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FORM_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getFormId() {
    return formId;
  }


  @JsonProperty(JSON_PROPERTY_FORM_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFormId(String formId) {
    this.formId = formId;
  }


  public WebformResponse fields(Map<String, String> fields) {
    
    this.fields = fields;
    return this;
  }

  public WebformResponse putFieldsItem(String key, String fieldsItem) {
    if (this.fields == null) {
      this.fields = new HashMap<>();
    }
    this.fields.put(key, fieldsItem);
    return this;
  }

   /**
   * Fields in the webform template.
   * @return fields
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FIELDS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, String> getFields() {
    return fields;
  }


  @JsonProperty(JSON_PROPERTY_FIELDS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFields(Map<String, String> fields) {
    this.fields = fields;
  }


  public WebformResponse events(Map<String, List<String>> events) {
    
    this.events = events;
    return this;
  }

  public WebformResponse putEventsItem(String key, List<String> eventsItem) {
    if (this.events == null) {
      this.events = new HashMap<>();
    }
    this.events.put(key, eventsItem);
    return this;
  }

   /**
   * Events in the webform template.
   * @return events
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_EVENTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, List<String>> getEvents() {
    return events;
  }


  @JsonProperty(JSON_PROPERTY_EVENTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEvents(Map<String, List<String>> events) {
    this.events = events;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WebformResponse webformResponse = (WebformResponse) o;
    return Objects.equals(this.formId, webformResponse.formId) &&
        Objects.equals(this.fields, webformResponse.fields) &&
        Objects.equals(this.events, webformResponse.events);
  }

  @Override
  public int hashCode() {
    return Objects.hash(formId, fields, events);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WebformResponse {\n");
    sb.append("    formId: ").append(toIndentedString(formId)).append("\n");
    sb.append("    fields: ").append(toIndentedString(fields)).append("\n");
    sb.append("    events: ").append(toIndentedString(events)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

