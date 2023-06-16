package com.tabber.tabby.respository;

import com.tabber.tabby.entity.PersonalProjectEntity;
import com.tabber.tabby.entity.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityRepository extends JpaRepository<UniversityEntity,Long> {

    @Query(value = "select count(*) from universities",nativeQuery = true)
    Integer getUniversityCount();

    @Query(value = "select * from universities offset ?1 limit ?2",nativeQuery = true)
    List<UniversityEntity> getUniversities(Integer offset, Integer limit);

}
