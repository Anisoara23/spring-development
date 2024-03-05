package org.example.dao;

import org.example.business.UniversityService;
import org.example.domain.Staff;
import org.example.repo.StaffRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PagingTest {
    @Autowired
    private StaffRepo staffRepo;
    @Autowired
    private UniversityService universityService;

    @Test
    void findPage() {
        UniversityFactory.fillUniversity(universityService);
        List<Staff> allStaff = universityService.findAllStaff();
        Staff firstStaff = allStaff.get(0);
        Pageable pageRequest = PageRequest.of(1, 5, Sort.by(Sort.Order.asc("member.lastName")));
        List<Staff> staffPage = staffRepo.findAll(pageRequest).getContent();
        assertTrue(staffPage.get(0).getMember().getLastName().compareTo(staffPage.get(1).getMember().getLastName()) < 0);
        assertTrue(staffPage.get(1).getMember().getLastName().compareTo(staffPage.get(2).getMember().getLastName()) < 0);
        assertTrue(staffPage.get(2).getMember().getLastName().compareTo(staffPage.get(3).getMember().getLastName()) < 0);
        assertTrue(staffPage.get(3).getMember().getLastName().compareTo(staffPage.get(4).getMember().getLastName()) < 0);
    }
}
