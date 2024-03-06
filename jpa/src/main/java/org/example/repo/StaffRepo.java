package org.example.repo;

import org.example.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "staff", collectionResourceRel = "staff")
public interface StaffRepo extends JpaRepository<Staff, Integer> {

    List<Staff> findByMemberLastName(String lastName);
}
