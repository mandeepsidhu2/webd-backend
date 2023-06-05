package com.tabber.tabby.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "custom_links")
@Builder(toBuilder = true)
public class CustomLinkEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long customLinkId;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "custom_link_user_id")
    @JsonProperty("custom_link_user_id")
    private UserEntity user;

    @JsonProperty("link_group")
    @Column(name = "link_group")
    private String linkGroup;

    @JsonProperty("group_id")
    @Column(name = "group_id")
    private Long groupId;

    @JsonProperty("link_type")
    @Column(name = "link_type")
    private String linkType;
}
