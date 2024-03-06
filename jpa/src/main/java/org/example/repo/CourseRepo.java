package org.example.repo;

import org.example.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {

    Optional<Course> findByName(String name);

    List<Course> findByDepartmentChairMemberLastName(String chair);

    List<Course> findByPrerequisitesId(int id);

    List<Course> findByCredits(int credits);
}
