package com.tabber.tabby.respository;

import com.tabber.tabby.entity.RankWidgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankWidgetRepository extends JpaRepository<RankWidgetEntity,Long> {

}
