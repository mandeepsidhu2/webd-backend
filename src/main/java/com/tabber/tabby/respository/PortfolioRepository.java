package com.tabber.tabby.respository;

import com.tabber.tabby.entity.PlanEntity;
import com.tabber.tabby.entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity,Long> {
    @Query(value = "select * from portfolios where portfolio_user_id=?1 limit 1",nativeQuery = true)
    PortfolioEntity getTopByPortfolioUserId(Long portfolioUserId);
}
