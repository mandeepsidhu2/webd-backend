package com.tabber.tabby.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserBasicRespone {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty( "email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("picture_url")
    private String pictureUrl;

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("resume_present")
    private Boolean resumePresent;

}
