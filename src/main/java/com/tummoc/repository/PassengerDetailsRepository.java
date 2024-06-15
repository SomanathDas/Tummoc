package com.tummoc.repository;

import com.tummoc.entity.PassengerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerDetailsRepository extends JpaRepository<PassengerDetails, Long> {
    PassengerDetails findTopByOrderByPassengerIdDesc();
}
