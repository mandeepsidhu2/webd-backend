package com.tabber.tabby.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder(toBuilder = true)
public class UserEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JsonProperty("sub")
    @Column(name = "sub")
    private String sub;

    @JsonProperty("picture_url")
    @Column(name = "picture_url")
    private String pictureUrl;

    @JsonProperty("locale")
    @Column(name = "locale")
    private String locale;

    @JsonProperty("resume_present")
    @Column(name = "resume_present")
    private Boolean resumePresent;

    @JsonProperty("rank_widgets")
    @OneToMany
    @JoinColumn(name = "rank_widget_user_id")
    private List<RankWidgetEntity> rankWidgets;

    @JsonProperty("contest_widgets")
    @OneToMany
    @JoinColumn(name = "contest_widget_user_id")
    private List<ContestWidgetEntity> contestWidgets;

    @JsonProperty("personal_projects")
    @OneToMany
    @JoinColumn(name = "personal_project_user_id")
    private List<PersonalProjectEntity> personalProjects;

    @JsonProperty("course_widgets")
    @OneToMany
    @JoinColumn(name = "course_user_id")
    private List<CourseWidgetEntity> courses;

    @JsonProperty("experience_widgets")
    @OneToMany
    @JoinColumn(name = "experience_user_id")
    private List<ExperienceWidgetEntity> experienceWidgets;

    @JsonProperty("portfolio")
    @JsonManagedReference
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private PortfolioEntity portfolio;

    @JsonProperty("custom_link_entity")
    @JsonManagedReference
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private CustomLinkEntity customLinkEntity;

    @JsonProperty("last_logged_in")
    @Column(name="last_logged_in")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoggedIn;


    @Column(name = "cookie_accepted")
    @JsonProperty("cookie_accepted")
    private Boolean cookieAccepted = false;

    @JsonProperty("created_at")
    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @JsonProperty("updated_at")
    @Column(name="updated_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @JsonProperty("plan_id")
    @Column(name = "plan_id")
    private Integer planId;

    @JsonProperty("updated_by")
    @Column(name = "updated_by")
    private Long updatedBy;

    @JsonProperty("user_type")
    @Column(name = "user_type")
    private String userType;
}
