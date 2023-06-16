package com.tabber.tabby.respository;

import com.tabber.tabby.entity.FrontendConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrontendConfigurationRepository extends JpaRepository<FrontendConfigurationEntity,Integer> {
    @Query(value = "select * from frontend_configurations where page_type=?1 and key=?2 limit 1",nativeQuery = true)
    FrontendConfigurationEntity getTopByPageTypeAndKey(String pageType,String key);

    @Query(value = "select * from frontend_configurations",nativeQuery = true)
    List<FrontendConfigurationEntity> getAll();
}
