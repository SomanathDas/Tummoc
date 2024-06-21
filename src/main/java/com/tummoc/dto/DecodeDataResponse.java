package com.tummoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecodeDataResponse {

    private String busNumber;
    private String validatedAt;

}
