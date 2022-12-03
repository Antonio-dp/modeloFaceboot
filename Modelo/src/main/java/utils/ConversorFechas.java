/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author jegav
 */
public class ConversorFechas {

    public static Calendar toCalendar(LocalDate date) {
        Date fechaDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaDate);
        return calendar;
    }

    public static LocalDate toLocalDate(Calendar date) {
        TimeZone tz = date.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        LocalDate localDate = LocalDateTime.ofInstant(date.toInstant(), zid).toLocalDate();
        return localDate;
    }
}
