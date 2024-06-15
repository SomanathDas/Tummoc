package com.tummoc.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class QRCodeServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeServiceTest.class);

    private String extractVehicleNumber(String data) {
        String replaced = data.replaceAll("\\s", "");
        logger.debug("Attempting to extract vehicle number from data: {}", data);

        Pattern pattern = Pattern.compile("K.*(?=B)");
        Matcher matcher = pattern.matcher(replaced);
        if (matcher.find()) {
            String vehicleNumber = matcher.group();
            logger.debug("Found vehicle number: {}", vehicleNumber);
            return vehicleNumber;
        }
        logger.debug("No vehicle number found in the data.");
        return null;
    }

    @Test
    public void testExtractVehicleNumber() {
        String testData1 = "http://rebrand.ly/tummoc?tummoc_qr=BMTC BUS KA01 FA228781Bangalore&ref=tummoc_india";
        String testData2 = "BMTC BUS KA57 F149251Bangalore";
        String testData3 = "KA57F265 j 5541B";
        String testData4 = "KA01 F 439 3";

        // Test case 1: Should return "KA01FA2287"
        assertEquals("KA01FA228781", extractVehicleNumber(testData1));

        // Test case 2: Should return "KA57F1491"
        assertEquals("KA57F149251", extractVehicleNumber(testData2));

        // Test case 3: Should return "KA57F1491"
        assertEquals("KA57F265j5541", extractVehicleNumber(testData3));

        // Test case 4: Should return null since there is no ending "B"
        assertNull(extractVehicleNumber(testData4));
    }
}
