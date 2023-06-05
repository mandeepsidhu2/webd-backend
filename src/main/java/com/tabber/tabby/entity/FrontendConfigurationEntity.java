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
@Table(name = "frontend_configurations")
@Builder(toBuilder = true)
@TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class)
public class FrontendConfigurationEntity {
    @Id
    @Column(name = "id")
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "page_type")
    @JsonProperty("page_type")
    private String pageType;

    @JsonProperty("key")
    @Column(name = "key")
    private String key;

    @Column(name = "value",columnDefinition = "jsonb")
    @Type(type = "jsonb-node")
    @JsonProperty("value")
    private JsonNode value;

}
