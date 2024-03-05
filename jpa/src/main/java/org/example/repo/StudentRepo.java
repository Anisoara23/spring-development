package org.example.repo;

import org.example.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Integer> {

    List<Student> findByFullTime(boolean fullTime);

    List<Student> findByAge(Integer age);

    List<Student> findByAttendeeLastName(String lastName);


    Optional<Student> findTopByOrderByAgeDesc();

    List<Student> findByAttendeeFirstNameAndAttendeeLastName(String firstName, String lastName);

    List<Student> findByAgeLessThan(int age);

    List<Student> findByAttendeeLastNameLike(String nameCriteria);

    Optional<Student> findFirstByOrderByAttendeeLastNameAsc();

    List<Student> findTop3ByOrderByAgeDesc();
}
