package com.tabber.tabby.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FrontendConfigRequest {

    @JsonProperty("page_type")
    String pageType;

    @JsonProperty("key")
    String key;

    @JsonProperty("value")
    JsonNode value;

}