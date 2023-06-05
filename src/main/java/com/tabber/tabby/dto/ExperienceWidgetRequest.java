package com.tabber.tabby.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ExperienceWidgetRequest {

    @JsonProperty("type")
    private String type;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("cloud_certification_link")
    private String cloudCertificationLink;

    // only used while updating, not creating
    @JsonProperty("invisible")
    Boolean invisible = false;

}
