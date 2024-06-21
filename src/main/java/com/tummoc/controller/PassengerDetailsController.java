package com.tummoc.controller;

import com.tummoc.dto.PassengerDetailsResponse;
import com.tummoc.entity.PassengerDetails;
import com.tummoc.service.PassengerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passenger")
public class PassengerDetailsController {

    @Autowired
    private PassengerDetailsService passengerDetailsService;

    @CrossOrigin(origins = {"http://127.0.0.1:5501", "http://localhost:63342"})
    @PostMapping("/save")
    public ResponseEntity<PassengerDetails> save(@RequestBody PassengerDetailsResponse request) {
        System.out.println(request);
        PassengerDetails passengerDetails = passengerDetailsService.save(request);
        return new ResponseEntity<>(passengerDetails, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<PassengerDetailsResponse> getPassengerDetails() {
        PassengerDetailsResponse passengerDetails = passengerDetailsService.getPassengerDetails();
        return new ResponseEntity<>(passengerDetails, HttpStatus.OK);
    }

}
