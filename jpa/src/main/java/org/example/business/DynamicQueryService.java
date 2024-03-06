package org.example.business;

import org.example.domain.Course;
import org.example.repo.CourseRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicQueryService {

    private CourseRepo courseRepo;

    public DynamicQueryService(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    public List<Course> findCoursesBySpecification(CourseFilter filter) {
        return courseRepo.findAll(filter.getSpecification());
    }

}
