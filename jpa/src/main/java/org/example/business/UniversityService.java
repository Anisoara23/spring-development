package org.example.business;


import org.example.domain.Course;
import org.example.domain.Department;
import org.example.domain.Person;
import org.example.domain.Staff;
import org.example.domain.Student;
import org.example.repo.CourseRepo;
import org.example.repo.DepartmentRepo;
import org.example.repo.StaffRepo;
import org.example.repo.StudentRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UniversityService {

    private final DepartmentRepo departmentRepo;

    private final StaffRepo staffRepo;

    private final StudentRepo studentRepo;

    private final CourseRepo courseRepo;

    public UniversityService(DepartmentRepo departmentRepo, StaffRepo staffRepo, StudentRepo studentRepo, CourseRepo courseRepo) {
        this.departmentRepo = departmentRepo;
        this.staffRepo = staffRepo;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    public Student createStudent(String firstName, String lastName, boolean fullTime, int age) {
        return studentRepo.save(new Student(new Person(firstName, lastName), fullTime, age));
    }

    public Staff createFaculty(String firstName, String lastName) {
        return staffRepo.save(new Staff(new Person(firstName, lastName)));
    }

    public Department createDepartment(String deptname, Staff deptChair) {
        return departmentRepo.save(new Department(deptname, deptChair));
    }

    public Course createCourse(String name, int credits, Staff professor, Department department) {
        return courseRepo.save(new Course(name, credits, professor, department));
    }

    public Course createCourse(String name, int credits, Staff professor, Department department, Course... prereqs) {
        Course c = new Course(name, credits, professor, department);
        for (Course p : prereqs) {
            c.addPrerequisite(p);
        }
        return courseRepo.save(c);
    }

    public List<Course> findAllCourses() {
        return courseRepo.findAll();
    }

    public List<Staff> findAllStaff() {
        return staffRepo.findAll();
    }

    public List<Department> findAllDepartments() {
        return departmentRepo.findAll();
    }

    public List<Student> findAllStudents() {
        return studentRepo.findAll();
    }

    public void deleteAll() {
        try {
            studentRepo.deleteAll();
            courseRepo.deleteAll();
            departmentRepo.deleteAll();
            staffRepo.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
