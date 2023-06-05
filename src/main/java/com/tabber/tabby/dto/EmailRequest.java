package com.tabber.tabby.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    @JsonProperty("subject")
    String subject;

    @JsonProperty("text")
    String text;

    @JsonProperty("email")
    String email;

    @JsonProperty("email_to")
    String emailTo ="tabberonline@gmail.com";
}