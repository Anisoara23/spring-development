package org.example.business;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.criteria.Predicate;
import org.example.domain.Course;
import org.example.domain.Department;
import org.example.domain.Staff;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.domain.QCourse.course;


public class CourseFilter {
    private Optional<Department> department = Optional.empty();
    private Optional<Integer> credits = Optional.empty();
    private Optional<Staff> instructor = Optional.empty();

    public static CourseFilter filterBy() {
        return new CourseFilter();
    }

    public CourseFilter department(Department department) {
        this.department = Optional.of(department);
        return this;
    }

    public CourseFilter credits(Integer credits) {
        this.credits = Optional.of(credits);
        return this;
    }

    public CourseFilter instructor(Staff instructor) {
        this.instructor = Optional.of(instructor);
        return this;
    }

    public Optional<Department> getDepartment() {
        return department;
    }

    public Optional<Integer> getCredits() {
        return credits;
    }

    public Optional<Staff> getInstructor() {
        return instructor;
    }

    public Specification<Course> getSpecification() {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            department.ifPresent(d ->
                    predicates.add(criteriaBuilder.equal(root.get("department"), d)));
            credits.ifPresent(c ->
                    predicates.add(criteriaBuilder.equal(root.get("credits"), c)));
            instructor.ifPresent(i ->
                    predicates.add(criteriaBuilder.equal(root.get("instructor"), i)));

            return criteriaBuilder.and(predicates.toArray(predicates.toArray(new Predicate[0])));
        });
    }

    public com.querydsl.core.types.Predicate getQueryDslPredicate() {
        BooleanBuilder predicate = new BooleanBuilder();
        department.ifPresent(dept -> predicate.and(course.department.eq(dept)));
        credits.ifPresent(cred -> predicate.and(course.credits.eq(cred)));
        instructor.ifPresent(instr -> predicate.and(course.instructor.eq(instr)));
        return predicate;
    }

    public Example<Course> getExampleProbe() {
        return Example.of(new Course(
                null,
                credits.orElse(null),
                instructor.orElse(null),
                department.orElse(null)
        ));
    }
}
