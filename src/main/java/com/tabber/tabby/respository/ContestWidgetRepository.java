package com.tabber.tabby.respository;

import com.tabber.tabby.entity.ContestWidgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestWidgetRepository extends JpaRepository<ContestWidgetEntity,Long> {
    @Query(value = "select * from contest_widgets where id=?1 limit 1",nativeQuery = true)
    ContestWidgetEntity getTopByWidgetId(Long id);
}