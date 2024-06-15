package com.tummoc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerDetailsResponse {

    private String passengerName;
    private String identificationType;
    private String identificationNumber;
    private String passPurchased;
    private String passValidFrom;
    private String passValidTill;
    private String passengerAvatar;
    private String passFare;
}
