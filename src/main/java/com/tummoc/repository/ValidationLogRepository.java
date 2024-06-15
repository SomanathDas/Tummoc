package com.tummoc.repository;

import com.tummoc.entity.QRCodeData;
import com.tummoc.entity.ValidationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationLogRepository extends JpaRepository<ValidationLog, Long> {

    ValidationLog findTopByOrderByValidatedAtDesc();
}
