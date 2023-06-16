package com.tabber.tabby.respository;

import com.tabber.tabby.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LogsRepository extends JpaRepository<LogEntity, Long> {
    @Query(value = "select * from logs where id=?1 limit 1",nativeQuery = true)
    LogEntity getTopByLogId(Long id);
}
