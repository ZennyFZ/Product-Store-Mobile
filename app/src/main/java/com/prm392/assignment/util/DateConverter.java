package com.prm392.assignment.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public static String convertDateFormat(String originalDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = originalFormat.parse(originalDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
