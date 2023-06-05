package com.tabber.tabby.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tabber.tabby.enums.WebsiteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "websites")
@Builder(toBuilder = true)
public class WebsiteEntity {
    @Id
    @Column(name = "id")
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer websiteId;

    @Column(name = "deep_link")
    @JsonProperty("deep_link")
    private String deepLink;

    @Column(name = "picture_url")
    @JsonProperty("picture_url")
    private String pictureUrl;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @JsonProperty("type")
    private WebsiteType type;

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
