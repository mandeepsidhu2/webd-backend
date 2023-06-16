package com.tabber.tabby.respository;

import com.tabber.tabby.entity.CourseWidgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseWidgetRepository extends JpaRepository<CourseWidgetEntity, Long> {
    @Query(value = "select * from course_widgets where id=?1 limit 1",nativeQuery = true)
    CourseWidgetEntity getTopByCourseId(Long id);
}
