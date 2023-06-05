package com.tabber.tabby.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "logs")
@Builder(toBuilder = true)
public class LogEntity {

    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("log_id")
    private Long logId;

    @JsonProperty("class_name")
    @Column(name = "class_name")
    private String className;

    @JsonProperty("type")
    @Column(name = "type")
    private String type;

    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Long userId;

}
