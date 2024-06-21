package com.tummoc.service;

import com.tummoc.dto.PassengerDetailsResponse;
import com.tummoc.entity.PassengerDetails;
import com.tummoc.repository.PassengerDetailsRepository;
import com.tummoc.utility.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        System.out.println(passengerDetails);
        PassengerDetails details = passengerDetailsRepository.save(passengerDetails);
        System.out.println("After Saving: " + details);
        return details;
    }


    public PassengerDetailsResponse getPassengerDetails() {
        PassengerDetails passengerDetails = passengerDetailsRepository.findTopByOrderByPassengerIdDesc();
        System.out.println("Getting Passenger details from database: " + passengerDetails);
        return getPassengerDetailsResponse(passengerDetails);
    }


    public PassengerDetailsResponse getPassengerDetailsResponse(PassengerDetails request) {
        PassengerDetailsResponse passengerDetailsResponse = PassengerDetailsResponse.builder()
                .passengerName(request.getPassengerName())
                .identificationType(request.getIdentificationType())
                .identificationNumber(request.getIdentificationNumber())
                .passPurchased(DateFormatter.formatDateTime(request.getPassPurchased()))
                .passValidFrom(DateFormatter.formatDateTime(request.getPassValidFrom()))
                .passValidTill(DateFormatter.formatDateTime(request.getPassValidTill()))
                .passengerAvatar(Base64.getEncoder().encodeToString(request.getPassengerAvatar()))
                .passFare(String.valueOf(request.getPassFare()))
                .build();

        System.out.println("Add Passenger details to Passenger details Response: " + passengerDetailsResponse);
        return passengerDetailsResponse;
    }
}
