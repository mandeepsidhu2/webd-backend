package com.tabber.tabby.respository;

import com.tabber.tabby.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailsRepository extends JpaRepository<EmailEntity,Long> {
    @Query(value = "select * from emails where profile_id=?1 limit 1",nativeQuery = true)
    EmailEntity getEmailDataByProfileId(Long id);
}