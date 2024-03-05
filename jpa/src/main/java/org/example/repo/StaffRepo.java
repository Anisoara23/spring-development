package org.example.repo;

import org.example.domain.Staff;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepo extends JpaRepository<Staff, Integer> {

    List<Staff> findByMemberLastName(String lastName);
}
