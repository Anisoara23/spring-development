package org.example.dao;

import org.example.business.CourseFilter;
import org.example.business.DynamicQueryService;
import org.example.business.UniversityService;
import org.example.domain.Department;
import org.example.domain.Person;
import org.example.domain.Staff;
import org.example.repo.DepartmentRepo;
import org.example.repo.StaffRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.repository.query.FluentQuery;

import static org.example.business.CourseFilter.filterBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ExampleTest {

    @Autowired
    private DynamicQueryService queryService;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private StaffRepo staffRepo;

    @Test
    void findByCriteria() {
        UniversityFactory.fillUniversity(universityService);
        Department humanities = departmentRepo.findOne(
                Example.of(new Department("humanities", null),
                        ExampleMatcher.matching()
                                .withIgnoreCase()))
                .get();
        Staff professorBlack = staffRepo.findBy(
                Example.of(new Staff(new Person(null, "black")),
                        ExampleMatcher.matching()
                                .withIgnoreCase()),
                FluentQuery.FetchableFluentQuery::firstValue
        );

        System.out.println('\n' + "*** All Humanities Courses");
        queryAndVerify(filterBy().department(humanities));

        System.out.println('\n' + "*** 4 credit courses");
        queryAndVerify(filterBy().credits(4));

        System.out.println('\n' + "*** Courses taught by Professor Black");
        queryAndVerify(filterBy().instructor(professorBlack));

        System.out.println('\n' + "*** Courses In Humanties, taught by Professor Black, 4 credits");
        queryAndVerify(filterBy()
                .department(humanities)
                .credits(4)
                .instructor(professorBlack));
    }


    private void queryAndVerify(CourseFilter filter) {
        queryService.filterCoursesByExample(filter)
                .forEach(course -> {
                    filter.getInstructor().ifPresent(i -> assertEquals(i, course.getInstructor()));
                    filter.getCredits().ifPresent(c -> assertEquals(c, course.getCredits()));
                    filter.getDepartment().ifPresent(prof -> assertEquals(prof, course.getDepartment()));
                    System.out.println(course);
                });
    }
}
