package com.test_task.restaurant.repositories;

import com.test_task.restaurant.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByBonusCardId (Long id);
    Optional<Client> findByContact(String contact);
}
