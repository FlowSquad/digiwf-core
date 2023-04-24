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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * SearchResult
 */
@JsonPropertyOrder({
  SearchResult.JSON_PROPERTY_GUID,
  SearchResult.JSON_PROPERTY_TEMPLATE_ID,
  SearchResult.JSON_PROPERTY_TEMPLATE_NAME,
  SearchResult.JSON_PROPERTY_TEMPLATE_TYPE,
  SearchResult.JSON_PROPERTY_LAST_CHANGE,
  SearchResult.JSON_PROPERTY_ACTIVE
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class SearchResult {
  public static final String JSON_PROPERTY_GUID = "guid";
  private String guid;

  public static final String JSON_PROPERTY_TEMPLATE_ID = "templateId";
  private String templateId;

  public static final String JSON_PROPERTY_TEMPLATE_NAME = "templateName";
  private String templateName;

  /**
   * Gets or Sets templateType
   */
  public enum TemplateTypeEnum {
    STATIC("TEMPLATE_STATIC"),
    
    DYNAMIC("TEMPLATE_DYNAMIC"),
    
    PAPER("TEMPLATE_PAPER");

    private String value;

    TemplateTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TemplateTypeEnum fromValue(String value) {
      for (TemplateTypeEnum b : TemplateTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_TEMPLATE_TYPE = "templateType";
  private TemplateTypeEnum templateType;

  public static final String JSON_PROPERTY_LAST_CHANGE = "lastChange";
  private String lastChange;

  public static final String JSON_PROPERTY_ACTIVE = "active";
  private Boolean active;

  public SearchResult() {
  }

  public SearchResult guid(String guid) {
    
    this.guid = guid;
    return this;
  }

   /**
   * Get guid
   * @return guid
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_GUID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getGuid() {
    return guid;
  }


  @JsonProperty(JSON_PROPERTY_GUID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setGuid(String guid) {
    this.guid = guid;
  }


  public SearchResult templateId(String templateId) {
    
    this.templateId = templateId;
    return this;
  }

   /**
   * Get templateId
   * @return templateId
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TEMPLATE_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTemplateId() {
    return templateId;
  }


  @JsonProperty(JSON_PROPERTY_TEMPLATE_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }


  public SearchResult templateName(String templateName) {
    
    this.templateName = templateName;
    return this;
  }

   /**
   * Get templateName
   * @return templateName
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TEMPLATE_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTemplateName() {
    return templateName;
  }


  @JsonProperty(JSON_PROPERTY_TEMPLATE_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }


  public SearchResult templateType(TemplateTypeEnum templateType) {
    
    this.templateType = templateType;
    return this;
  }

   /**
   * Get templateType
   * @return templateType
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TEMPLATE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public TemplateTypeEnum getTemplateType() {
    return templateType;
  }


  @JsonProperty(JSON_PROPERTY_TEMPLATE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTemplateType(TemplateTypeEnum templateType) {
    this.templateType = templateType;
  }


  public SearchResult lastChange(String lastChange) {
    
    this.lastChange = lastChange;
    return this;
  }

   /**
   * Get lastChange
   * @return lastChange
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LAST_CHANGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getLastChange() {
    return lastChange;
  }


  @JsonProperty(JSON_PROPERTY_LAST_CHANGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLastChange(String lastChange) {
    this.lastChange = lastChange;
  }


  public SearchResult active(Boolean active) {
    
    this.active = active;
    return this;
  }

   /**
   * Get active
   * @return active
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ACTIVE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getActive() {
    return active;
  }


  @JsonProperty(JSON_PROPERTY_ACTIVE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setActive(Boolean active) {
    this.active = active;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchResult searchResult = (SearchResult) o;
    return Objects.equals(this.guid, searchResult.guid) &&
        Objects.equals(this.templateId, searchResult.templateId) &&
        Objects.equals(this.templateName, searchResult.templateName) &&
        Objects.equals(this.templateType, searchResult.templateType) &&
        Objects.equals(this.lastChange, searchResult.lastChange) &&
        Objects.equals(this.active, searchResult.active);
  }

  @Override
  public int hashCode() {
    return Objects.hash(guid, templateId, templateName, templateType, lastChange, active);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchResult {\n");
    sb.append("    guid: ").append(toIndentedString(guid)).append("\n");
    sb.append("    templateId: ").append(toIndentedString(templateId)).append("\n");
    sb.append("    templateName: ").append(toIndentedString(templateName)).append("\n");
    sb.append("    templateType: ").append(toIndentedString(templateType)).append("\n");
    sb.append("    lastChange: ").append(toIndentedString(lastChange)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
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

