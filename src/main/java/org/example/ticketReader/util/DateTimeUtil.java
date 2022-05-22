package org.example.ticketReader.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    public static Date dateTimeParser(String date, String time) {

        StringBuilder builder = new StringBuilder();
        builder.append(date).append(" ").append(time);
        Date parsedDate;
        String pattern = "dd.MM.yy HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            parsedDate = format.parse(builder.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return parsedDate;
    }
}
