package org.example.business;

import org.example.domain.Course;
import org.example.repo.CourseQueryDslRepo;
import org.example.repo.CourseRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicQueryService {

    private final CourseRepo courseRepo;
    private final CourseQueryDslRepo courseQueryDslRepo;

    public DynamicQueryService(CourseRepo courseRepo, CourseQueryDslRepo courseQueryDslRepo) {
        this.courseRepo = courseRepo;
        this.courseQueryDslRepo = courseQueryDslRepo;
    }

    public List<Course> findCoursesBySpecification(CourseFilter filter) {
        return courseRepo.findAll(filter.getSpecification());
    }

    public List<Course> findCoursesByQueryDsl(CourseFilter filter) {
        List<Course> courses = new ArrayList<>();
        courseQueryDslRepo.findAll(filter.getQueryDslPredicate())
                .forEach(courses::add);
        return courses;
    }

    public List<Course> filterCoursesByExample(CourseFilter filter) {
        return courseRepo.findAll(filter.getExampleProbe());
    }
}
