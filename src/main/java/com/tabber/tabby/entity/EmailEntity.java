package com.tabber.tabby.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "emails")
@Builder(toBuilder = true)
@TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class)
public class EmailEntity {

    @Id
    @Column(name = "profile_id")
    @JsonProperty("profile_id")
    private Long id;

    @Column(name = "email_data",columnDefinition = "jsonb")
    @Type(type = "jsonb-node")
    @JsonProperty("email_data")
    private JsonNode emailData;

}