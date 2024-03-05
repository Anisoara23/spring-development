package org.example.dao;

import org.example.business.UniversityService;
import org.example.domain.Student;
import org.example.repo.StudentRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FindByClausesAndExpressions {

    @Autowired
    private UniversityService universityService;

    @Autowired
    private StudentRepo studentRepo;

    @Test
    public void findByClausesAndExpressions() {
        // Test Create
        UniversityFactory.fillUniversity(universityService);
        List<Student> students = universityService.findAllStudents();
        Student firstStudent = students.get(0);

        assertTrue(studentRepo.findOldest().get().getAge() == 22);

        assertEquals(firstStudent, studentRepo.findByFirstAndLastName(firstStudent.getAttendee().getFirstName(),
                firstStudent.getAttendee().getLastName()).get(0));

        studentRepo.findByAgeLessThan(20).stream().forEach(s -> assertTrue(s.getAge() < 20));

        studentRepo.findSimilarLastName("%o%")
                .stream().forEach(s -> assertTrue(s.getAttendee().getLastName().contains("o")));

        assertTrue(studentRepo.findFirstInAlphabet().get().getAttendee().getLastName().equals("Doe"));

        List<Student> students3 = studentRepo.find3Oldest();
        assertTrue(students3.size() == 3);
        students3.stream().forEach(s -> assertTrue(s.getAge() != 18));
    }
}
