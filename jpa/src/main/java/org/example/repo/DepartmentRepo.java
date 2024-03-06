package org.example.repo;

import org.example.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

    Optional<Department> findByName(String name);
}
