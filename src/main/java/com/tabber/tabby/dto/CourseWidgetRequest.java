package com.tabber.tabby.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CourseWidgetRequest {

    @JsonProperty("course_name")
    String courseName;

    @JsonProperty("issuer")
    String issuer;

    // only used while updating, not creating
    @JsonProperty("invisible")
    Boolean invisible = false;

    @JsonProperty("certificate_link")
    String certificateLink;
}
