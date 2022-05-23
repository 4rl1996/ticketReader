package org.example.ticketReader.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    private final static String PATTERN = "dd.MM.yy HH:mm";

    public static Date dateTimeParser(String date, String time) {

        StringBuilder builder = new StringBuilder();
        builder.append(date).append(" ").append(time);
        Date parsedDate;
        SimpleDateFormat format = new SimpleDateFormat(PATTERN);
        try {
            parsedDate = format.parse(builder.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return parsedDate;
    }
}
