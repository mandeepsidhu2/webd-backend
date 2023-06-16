package com.tabber.tabby.respository;

import com.tabber.tabby.entity.ExperienceWidgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExperienceWidgetRepository extends JpaRepository<ExperienceWidgetEntity, Long> {
    @Query(value = "select * from experience_widgets where id=?1 limit 1",nativeQuery = true)
    ExperienceWidgetEntity getTopByExperienceId(Long id);
}
