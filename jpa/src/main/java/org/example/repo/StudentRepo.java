package org.example.repo;

import org.example.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Integer> {

    List<Student> findByFullTime(boolean fullTime);

    List<Student> findByAge(Integer age);

    List<Student> findByAttendeeLastName(String lastName);

    @Query(value = "select * from Student s order by s.age desc limit 1", nativeQuery = true)
    Optional<Student> findOldest();

    @Query("select s from Student s join attendee a where a.firstName = :firstName and a.lastName = :lastName")
    List<Student> findByFirstAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    List<Student> findByAgeLessThan(int age);

    @Query("select s from Student s join attendee a where a.lastName like :nameCriteria")
    List<Student> findSimilarLastName(String nameCriteria);

    @Query(value = "select * from Student s order by s.last_name asc limit 1", nativeQuery = true)
    Optional<Student> findFirstInAlphabet();

    @Query(value = "select * from Student s order by s.age desc limit 3", nativeQuery = true)
    List<Student> find3Oldest();
}
