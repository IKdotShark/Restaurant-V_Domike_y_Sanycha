package com.test_task.restaurant.repositories;

import com.test_task.restaurant.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByStaffRole(Employee.StaffRole role);
}
