package mk.ukim.finki.initiativesservice.model.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utilities {
//    private static final String timeFormat = "yyyy-MM-dd HH:mm";
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);

    public static LocalDateTime convertFromStringToDateAndTime(String dateAndTime) {
        return LocalDateTime.parse(dateAndTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
