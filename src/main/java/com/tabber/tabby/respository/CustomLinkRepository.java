package com.tabber.tabby.respository;

import com.tabber.tabby.entity.CustomLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomLinkRepository extends JpaRepository<CustomLinkEntity, Long> {

    @Query(value = "select * from custom_links where link_group=?1 and group_id=?2 and link_type=?3 limit 1",nativeQuery = true)
    CustomLinkEntity getCustomLink(String linkGroup, Long groupId, String linkType);

    @Query(value = "select * from custom_links where custom_link_user_id=?1  limit 1",nativeQuery = true)
    CustomLinkEntity getCustomLinkFromUserId(Long userId);



}