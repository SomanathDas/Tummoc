package com.tummoc.service;

import com.tummoc.dto.PassengerDetailsResponse;
import com.tummoc.entity.PassengerDetails;
import com.tummoc.repository.PassengerDetailsRepository;
import com.tummoc.utility.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;

@Service
public class PassengerDetailsService {

    @Autowired
    private PassengerDetailsRepository passengerDetailsRepository;

    public PassengerDetails save(PassengerDetailsResponse request) {

        byte[] passengerAvatar = Base64.getDecoder().decode(request.getPassengerAvatar());

        PassengerDetails passengerDetails = PassengerDetails.builder()
                        .passengerName(request.getPassengerName())
                        .identificationType(request.getIdentificationType())
                        .identificationNumber(request.getIdentificationNumber())
                        .passPurchased(LocalDateTime.parse(request.getPassPurchased()))
                        .passValidFrom(LocalDateTime.parse(request.getPassValidFrom()))
                        .passValidTill(LocalDateTime.parse(request.getPassValidTill()))
                        .passengerAvatar(passengerAvatar)
                        .passFare(Integer.parseInt(request.getPassFare()))
                        .build();
        return passengerDetailsRepository.save(passengerDetails);
    }


    public PassengerDetailsResponse getPassengerDetails() {
        PassengerDetails passengerDetails = passengerDetailsRepository.findTopByOrderByPassengerIdDesc();
        return getPassengerDetailsResponse(passengerDetails);
    }


    public PassengerDetailsResponse getPassengerDetailsResponse(PassengerDetails request) {
        return PassengerDetailsResponse.builder()
               .passengerName(request.getPassengerName())
               .identificationType(request.getIdentificationType())
               .identificationNumber(request.getIdentificationNumber())
               .passPurchased(DateFormatter.formatDateWithWeek(request.getPassPurchased()))
               .passValidFrom(DateFormatter.formatDateWithWeek(request.getPassValidFrom()))
               .passValidTill(DateFormatter.formatDateWithWeek(request.getPassValidTill()))
               .passengerAvatar(Base64.getEncoder().encodeToString(request.getPassengerAvatar()))
               .passFare(String.valueOf(request.getPassFare()))
               .build();
    }
}
