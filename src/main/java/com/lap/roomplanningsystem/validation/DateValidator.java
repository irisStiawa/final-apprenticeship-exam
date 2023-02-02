package com.lap.roomplanningsystem.validation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class DateValidator {

    public static boolean oneTime(LocalDate day){
        return !isWeekend(day) && !isHoliday(day);
    }


    public static ObservableList<LocalDate> daily(LocalDate startDate, LocalDate endDate) {
        ObservableList<LocalDate> days = FXCollections.observableArrayList();

        LocalDate day = startDate;

        for(int i = 1; day.isBefore(endDate.plusDays(1)); i++){
            if(!isWeekend(day) && !isHoliday(day)){
                days.add(day);
            }

            day = startDate.plusDays(i);
        }

        return days;
    }




    public static ObservableList<LocalDate> weekly(LocalDate startDate, LocalDate endDate){
        ObservableList<LocalDate> days = FXCollections.observableArrayList();

        LocalDate day = startDate;

        for(int i = 1; day.isBefore(endDate.plusDays(1)); i++){

            if(!isWeekend(day) && !isHoliday(day)){
                days.add(day);
            }

            day = startDate.plusWeeks(i);
        }

        return days;
    }


    public static ObservableList<LocalDate> monthly(LocalDate startDate, LocalDate endDate) {
        ObservableList<LocalDate> days = FXCollections.observableArrayList();

        LocalDate day = startDate;

        for(int i = 1; day.isBefore(endDate.plusDays(1)); i++){
            if(!isWeekend(day) && !isHoliday(day)){
                days.add(day);
            }

            day = startDate.plusMonths(i);
        }

        return days;
    }

    private static boolean isWeekend(final LocalDate ld)
    {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }

    private static boolean isHoliday(LocalDate day) {
        LocalDate easter = calculateEaster(day.getYear());

        if(day.isEqual(LocalDate.of(day.getYear(), 1, 1))) {
            return true;
        } else if (day.isEqual(LocalDate.of(day.getYear(), 1, 6))){
            return true;
        } else if(day.isEqual(easter.minusDays(2))){
            return true;
        }else if(day.isEqual(easter.plusDays(1))){
            return true;
        }else if(day.isEqual(LocalDate.of(day.getYear(), 5, 1))){
            return true;
        }else if(day.isEqual(easter.plusDays(39))) {
            return true;
        }else if(day.isEqual(easter.plusDays(49))) {
            return true;
        }else if(day.isEqual(easter.plusDays(50))) {
            return true;
        }else if(day.isEqual(easter.plusDays(60))) {
            return true;
        }else if(day.isEqual(LocalDate.of(day.getYear(), 8, 15))) {
            return true;
        }else if(day.isEqual(LocalDate.of(day.getYear(), 10, 26))) {
            return true;
        }else if(day.isEqual(LocalDate.of(day.getYear(), 11, 1))) {
            return true;
        }else if(day.isEqual(LocalDate.of(day.getYear(), 12, 8))) {
            return true;
        }else if(day.isEqual(LocalDate.of(day.getYear(), 12, 24))) {
            return true;
        }else if(day.isEqual(LocalDate.of(day.getYear(), 12, 25))) {
            return true;
        }else if(day.isEqual(LocalDate.of(day.getYear(), 12, 26))) {
            return true;
        }else if(day.isEqual(LocalDate.of(day.getYear(), 12, 31))) {
            return true;
        } else {
            return false;
        }
    }

    private static LocalDate calculateEaster(int year){

        int a = year % 19;
        int b = year % 4;
        int c = year % 7;
        int month = 0;

        int m = (8 * (year / 100) + 13) / 25 - 2;
        int s = year / 100 - year / 400 - 2;
        m = (15 + s - m) % 30;
        int n = (6 + s) % 7;
        int d = (m + 19 * a) % 30;

        if ( d == 29 ) {
            d = 28;
        } else if (d == 28 && a >= 11) {
            d = 27;
        }

        int e = (2 * b + 4 * c + 6 * d + n) % 7;
        int day = 21 + d + e + 1;
        if (day > 31) {
            day = day % 31;
            month = 4;
        } else if (day <= 31) {
            month = 3; //TODO: easter not correct
        }

        return LocalDate.of(year, month, day);
    }

    public static boolean validDate(LocalDate value) {
        return !value.isBefore(LocalDate.now());
    }


    public static boolean validSerie(LocalDate start, LocalDate end) {
        return end.isAfter(start);
    }

    public static boolean validTime(LocalTime start, LocalTime end) {
        return start.isBefore(end);
    }
}
