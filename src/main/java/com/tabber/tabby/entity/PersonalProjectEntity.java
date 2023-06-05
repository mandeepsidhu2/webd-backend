package com.tabber.tabby.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "personal_projects")
@Builder(toBuilder = true)
public class PersonalProjectEntity {
    @Id
    @Column(name = "id")
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personalProjectId;

    @Column(name = "title")
    @JsonProperty("title")
    private String title;

    @Column(name ="personal_project_user_id")
    @JsonProperty("personal_project_user_id")
    private Long userId;

    @Column(name = "link")
    @JsonProperty("link")
    private String link;

    @Column(name = "tech_stack",columnDefinition = "jsonb")
    @Type(type = "jsonb-node")
    @JsonProperty("tech_stack")
    private JsonNode techStack;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "invisible")
    @JsonProperty("invisible")
    private Boolean invisible = false;

    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @JsonProperty("created_at")
    private Date createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("updated_at")
    private Date updatedAt;


}
