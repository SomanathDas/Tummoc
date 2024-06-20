package com.tummoc.service;

import com.tummoc.dto.DecodeDataResponse;
import com.tummoc.entity.QRCodeData;
import com.tummoc.entity.ValidationLog;
import com.tummoc.repository.QRCodeDataRepository;
import com.tummoc.repository.ValidationLogRepository;
import com.tummoc.utility.DateFormatter;
import com.tummoc.utility.ExtractBusNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tummoc.utility.ExtractBusNumber.extractVehicleNumber;

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
        logger.info(String.valueOf(lastValidated));
        DecodeDataResponse decodeDataResponse = new DecodeDataResponse(lastValidated.getBusNumber(), DateFormatter.formatDateTime(lastValidated.getValidatedAt()));
        logger.info(String.valueOf(decodeDataResponse));
        return decodeDataResponse;
    }

}
