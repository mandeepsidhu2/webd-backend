package com.tabber.tabby.respository;

import com.tabber.tabby.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlansRepository extends JpaRepository<PlanEntity, Long> {

    @Query(value = "select * from plans where plan_id=?1 limit 1",nativeQuery = true)
    PlanEntity getTopByPlanId(Integer planId);
}
