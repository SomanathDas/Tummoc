package com.tummoc.service;

import com.tummoc.dto.DecodeDataResponse;
import com.tummoc.entity.QRCodeData;
import com.tummoc.entity.ValidationLog;
import com.tummoc.repository.QRCodeDataRepository;
import com.tummoc.repository.ValidationLogRepository;
import com.tummoc.utility.DateFormatter;
import com.tummoc.utility.ExtractBusNumber;
import com.tummoc.utility.TimeZoneConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class QRCodeService {

    private final Logger logger= LoggerFactory.getLogger(QRCodeService.class);

    @Autowired
    private QRCodeDataRepository qrCodeDataRepository;

    @Autowired
    private ValidationLogRepository validationLogRepository;


    public DecodeDataResponse saveQRCode(String data){
        logger.info("Received QR code data: {}", data);
        String vehicleNumber = ExtractBusNumber.extractVehicleNumber(data);
        if (vehicleNumber != null) {
            logger.info("Extracted vehicle number: {}", vehicleNumber);
        } else {
            logger.warn("No vehicle number found in the data: {}", data);
        }

        LocalDateTime dateTime = LocalDateTime.now();
        String date = String.valueOf(dateTime);
        LocalDateTime localDateTime = LocalDateTime.parse(date);
        System.out.println("converted date: " + localDateTime);


        ValidationLog validationLog = new ValidationLog();
        validationLog.setBusNumber(vehicleNumber);
        validationLog.setValidatedAt(localDateTime);
        ValidationLog validationLog1 = validationLogRepository.save(validationLog);
        System.out.println("Validation Log after save: " + validationLog1);

        QRCodeData qrCodeData = new QRCodeData();
        qrCodeData.setData(data);
        qrCodeData.setVehicleNumber(vehicleNumber);
        qrCodeDataRepository.save(qrCodeData);

        logger.info("QR code data saved successfully.");
        String timeStamp = DateFormatter.formatDateTime(dateTime);
        return new DecodeDataResponse(vehicleNumber, timeStamp);
    }


    public DecodeDataResponse getLatestQRCodeData() {
        logger.info("Getting latest QR code data.");
        ValidationLog lastValidated = validationLogRepository.findTopByOrderByValidatedAtDesc();
        if (lastValidated == null) {
            logger.warn("No QR code data found.");
            return null;
        }

        String convertedDateTime = TimeZoneConversion.convertOregonToIST(lastValidated.getValidatedAt());

        logger.info("converted date: {}", convertedDateTime);

        DecodeDataResponse decodeDataResponse = new DecodeDataResponse(lastValidated.getBusNumber(), convertedDateTime);
        logger.info(String.valueOf(decodeDataResponse));
        return decodeDataResponse;
    }

    private ZonedDateTime convertToIST(LocalDateTime dateTime) {
        // Convert from UTC to IST
        return dateTime.atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
    }

}
