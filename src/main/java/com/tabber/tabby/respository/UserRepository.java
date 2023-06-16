package com.tabber.tabby.respository;

import com.tabber.tabby.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query(value = "select * from users where user_id=?1 limit 1",nativeQuery = true)
    UserEntity getTopByUserId(Long userId);

    @Query(value = "select * from users where email=?1 limit 1",nativeQuery = true)
    UserEntity getTopByEmailId(String email);

    @Query(value = "select * from users where sub=?1 limit 1",nativeQuery = true)
    UserEntity getTopBySub(String sub);

    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<UserEntity> getUsersFromLimitAndOffset(Pageable pageable);

    @Query(value = "SELECT * FROM users WHERE name ~*:nameSearch", nativeQuery = true)
    List<UserEntity> getSimilarNameUsers(@Param("nameSearch")String name, Pageable pageable);

    @Query(value = "SELECT * FROM users WHERE email=?1", nativeQuery = true)
    UserEntity getUserFromEmail(String email);

    @Query(value = "SELECT * FROM users WHERE user_id=?1", nativeQuery = true)
    UserEntity getUserFromId(Long id);

    @Query(value = "SELECT * FROM users WHERE plan_id=?1", nativeQuery = true)
    List<UserEntity> getSimilarPlanUsers(Integer plan_id, Pageable pageable);
}
