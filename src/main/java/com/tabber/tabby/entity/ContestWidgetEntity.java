package com.tabber.tabby.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contest_widgets")
@Builder(toBuilder = true)
public class ContestWidgetEntity {
    @Id
    @Column(name = "id")
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contestWidgetId;

    @Column(name = "website_id")
    @JsonProperty("website_id")
    private Integer websiteId;

    @Column(name = "rank")
    @JsonProperty("rank")
    private Integer rank;

    @Column(name = "contest_name")
    @JsonProperty("contest_name")
    private String contestName;

    @Column(name = "website_username")
    @JsonProperty("website_username")
    private String websiteUsername;

    @Column(name ="contest_widget_user_id")
    @JsonProperty("contest_widget_user_id")
    private Long userId;

    @Column(name = "invisible")
    @JsonProperty("invisible")
    private Boolean invisible = false;

    @Column(name="created_at")
    @JsonProperty("created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    @JsonProperty("updated_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
