package com.tummoc.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractBusNumber {

    public static String extractVehicleNumber(String data) {
        String dataWithoutSpaces = data.replaceAll("\\s", "");

        Pattern pattern = Pattern.compile("K.*(?=B)");
        Matcher matcher = pattern.matcher(dataWithoutSpaces);
        if (matcher.find()){
            String vehicleNumber = matcher.group();
            return matcher.group();
        }
        return null;
    }
}
