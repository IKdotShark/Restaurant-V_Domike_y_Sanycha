package com.test_task.restaurant.repositories;

import com.test_task.restaurant.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}
