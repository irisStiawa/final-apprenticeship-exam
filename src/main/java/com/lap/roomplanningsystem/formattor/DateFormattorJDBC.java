package com.lap.roomplanningsystem.formattor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateFormattorJDBC {


    public static LocalDateTime format(LocalDate date, LocalTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String startDate = date.toString() + " "+ time.toString();
        return LocalDateTime.parse(startDate, formatter);

    }


}
