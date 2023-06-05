package com.tabber.tabby.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plans")
@Builder(toBuilder = true)
public class PlanEntity {

    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("plan_id")
    private Integer planId;

    @JsonProperty("rank_widget_max")
    @Column(name = "rank_widget_max")
    private Integer rankWidgetMax;

    @JsonProperty("contest_widget_max")
    @Column(name = "contest_widget_max")
    private Integer contestWidgetMax;

    @JsonProperty("personal_project_max")
    @Column(name = "personal_project_max")
    private Integer personalProjectMax;

    @JsonProperty("email_per_day_limit")
    @Column(name = "email_per_day_limit")
    private Integer emailPerDayLimit;

    @JsonProperty("courses_limit")
    @Column(name = "courses_limit")
    private Integer coursesLimit;

    @JsonProperty("experience_limit")
    @Column(name = "experience_limit")
    private Integer experienceLimit;

    @JsonProperty("plan_name")
    @Column(name = "plan_name")
    private String planName;

    @JsonProperty("plan_price")
    @Column(name = "plan_price")
    private Integer planPrice;

    @JsonProperty("country_id")
    @Column(name = "country_id")
    private String countryId;

}
