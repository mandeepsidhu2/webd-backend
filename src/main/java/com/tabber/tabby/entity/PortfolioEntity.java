package com.tabber.tabby.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabber.tabby.dto.SocialWebsiteDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.json.JSONArray;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "portfolios")
@Builder(toBuilder = true)
public class PortfolioEntity {
    @Id
    @Column(name = "id")
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;

    @Column(name = "title")
    @JsonProperty("title")
    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "portfolio_user_id")
    @JsonProperty("portfolio_user_id")
    private UserEntity user;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "cloud_resume_link")
    @JsonProperty("cloud_resume_link")
    private String cloudResumeLink;

    @Column(name = "college")
    @JsonProperty("college")
    private Integer college;

    @Column(name = "college_others")
    @JsonProperty("college_others")
    private String collegeOthers;

    @Column(name = "graduation_year")
    @JsonProperty("graduation_year")
    private Integer graduationYear;

    @Column(name = "education_level")
    @JsonProperty("education_level")
    private String educationLevel;

    @Column(name = "social_profiles",columnDefinition = "jsonb")
    @Type(type = "jsonb-node")
    @JsonProperty("social_profiles")
    private JsonNode socialProfiles;

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

    @JsonProperty("views")
    @Column(name = "views")
    private Long views;

    @JsonProperty("untracked_views")
    @Column(name = "untracked_views")
    private Long untrackedViews;

    public ArrayList<SocialWebsiteDto> getSocialProfiles(){
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<SocialWebsiteDto> socialWebsiteDtoList = objectMapper.convertValue(this.socialProfiles, new TypeReference<>(){});
        return socialWebsiteDtoList;
    }

    public void setSocialProfiles(ArrayList<SocialWebsiteDto> socialWebsiteDtoList) {
        JSONArray socialWebsiteArray = new JSONArray(socialWebsiteDtoList);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(socialWebsiteArray.toString());
            this.socialProfiles = jsonNode;
        } catch (Exception ex) {
            return;
        }
    }
}
