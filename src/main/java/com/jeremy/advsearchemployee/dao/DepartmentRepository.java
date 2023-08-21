package com.jeremy.advsearchemployee.dao;

import com.jeremy.advsearchemployee.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
