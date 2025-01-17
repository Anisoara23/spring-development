package org.example.dao;

import org.example.business.UniversityService;
import org.example.domain.Course;
import org.example.domain.Department;
import org.example.domain.Staff;
import org.example.domain.Student;

public class UniversityFactory {

    public static void fillUniversity(UniversityService service) {
        service.deleteAll();
        boolean fullTime = true;
        Student jane = service.createStudent("jane", "Doe", fullTime, 20);
        Student john = service.createStudent("john", "Doe", fullTime, 22);
        Student mike = service.createStudent("mike", "Smith", fullTime, 18);
        Student ally = service.createStudent("ally", "Kim", !fullTime, 19);
        Staff deanThomas = service.createFaculty("Tom", "Thomas");
        Staff deanGreen = service.createFaculty("Graham", "Green");
        Staff deanJones = service.createFaculty("John", "Jones");
        Staff deanMartin = service.createFaculty("Matthew", "Martin");
        Staff profBrown = service.createFaculty("James", "Brown");
        Staff profMiller = service.createFaculty("Judy", "Miller");
        Staff profDavis = service.createFaculty("James", "Davis");
        Staff profMoore = service.createFaculty("Allison", "Moore");
        Staff profWhite = service.createFaculty("Whitney", "White");
        Staff profBlack = service.createFaculty("Jack", "Black");
        Staff profKing = service.createFaculty("Queen", "King");
        //Departments
        Department humanities = service.createDepartment("Humanities", deanJones);
        Department naturalSciences = service.createDepartment("Natural Sciences", deanMartin);
        Department socialSciences = service.createDepartment("Social Sciences", deanGreen);

        //Humanities Courses

        Course english101 = service.createCourse("English 101", 3, profBlack, humanities);
        Course english201 = service.createCourse("English 201", 3, profBrown, humanities, english101);

        //Natural Science Courses
        Course chemistry = service.createCourse("Chemistry", 3, profDavis, naturalSciences);
        Course cProgramming = service.createCourse("C Programming", 4, profMoore, naturalSciences);

        //Social Science Courses
        Course history101 = service.createCourse("History 101", 3, profMiller, socialSciences);
        Course anthro = service.createCourse("Anthropology ", 3, profKing, socialSciences, history101);
        Course psych = service.createCourse("Psychology", 3, profWhite, socialSciences, english101);
    }
}
