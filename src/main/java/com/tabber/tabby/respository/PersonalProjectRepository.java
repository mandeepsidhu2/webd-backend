package com.tabber.tabby.respository;

import com.tabber.tabby.entity.PersonalProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalProjectRepository extends JpaRepository<PersonalProjectEntity,Long> {

    @Query(value = "select * from personal_projects where id=?1 limit 1",nativeQuery = true)
    PersonalProjectEntity getTopByProjectId(Long id);
}
