package com.tabber.tabby.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalProjectRequest {

    @JsonProperty("title")
    String title;

    @JsonProperty("link")
    String link;

    @JsonProperty("tech_stack")
    ArrayNode techStack;

    @JsonProperty("description")
    String description;

    // only used while updating, not creating
    @JsonProperty("invisible")
    Boolean invisible = false;

}