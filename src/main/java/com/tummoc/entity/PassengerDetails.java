package com.tummoc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PassengerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long passengerId;
    private String passengerName;
    private String identificationType;
    private String identificationNumber;
    private LocalDateTime passPurchased;
    private LocalDateTime passValidFrom;
    private LocalDateTime passValidTill;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] passengerAvatar;
    private int passFare;
}
