package com.tummoc.controller;

import com.tummoc.dto.DecodeDataResponse;
import com.tummoc.dto.QRCodePayload;
import com.tummoc.service.QRCodeService;
import com.tummoc.utility.QRCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/qrcode")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    private final Logger logger =LoggerFactory.getLogger(QRCodeController.class);

    @PostMapping("/upload")
    public ResponseEntity<String> uploadQRCode(@RequestParam("file") MultipartFile file) {
        try {
            String qrCodeText = QRCodeUtil.decodeQRCode(file.getBytes());
            if (qrCodeText == null) {
                return ResponseEntity.badRequest().body("Could not decode QR code");
            }
            qrCodeService.saveQRCode(qrCodeText);
            return new ResponseEntity<>("QR code data saved successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to process QR code", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @CrossOrigin(origins = "http://localhost:63342")
    @PostMapping("/decode")
    public ResponseEntity<DecodeDataResponse> decodeQRCode(@RequestBody QRCodePayload payload) {
        String decodeMessage = payload.getQrData();
        logger.info("decodeMessage: {}",decodeMessage);
        System.out.println("decodeMessage: {}"+decodeMessage);
        if (decodeMessage == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String message = decodeMessage.replaceAll("\"", "");
        DecodeDataResponse decodeDataResponse = qrCodeService.saveQRCode(message);
        logger.info("decodeMessage: {}",decodeMessage);
        return new ResponseEntity<>(decodeDataResponse, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/last-validation")
    public ResponseEntity<DecodeDataResponse> getLastQRCode() {
        DecodeDataResponse latestQRCodeData = qrCodeService.getLatestQRCodeData();
        return new ResponseEntity<>(latestQRCodeData, HttpStatus.OK);
    }


}
