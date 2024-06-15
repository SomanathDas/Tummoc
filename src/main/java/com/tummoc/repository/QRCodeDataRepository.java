package com.tummoc.repository;

import com.tummoc.entity.QRCodeData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QRCodeDataRepository extends JpaRepository<QRCodeData, Long> {
}
