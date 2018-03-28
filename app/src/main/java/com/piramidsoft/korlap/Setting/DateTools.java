package com.piramidsoft.korlap.Setting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Tambora on 29/11/2016.
 */
public class DateTools {

    public static HashMap<String, String> getNow() {

        return dateProsses(null, null);
    }


    public static HashMap<String, String> getDateString(String timestamp, String format) {

        return dateProsses(timestamp, format);
    }


    private static HashMap<String, String> dateProsses(String timestamp, String dformat) {

        Calendar calendar = Calendar.getInstance();

        if (timestamp != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(dformat, Locale.ENGLISH);
            try {
                calendar.setTime(sdf.parse(timestamp));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int mont = calendar.get(Calendar.MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String days = null;
        String months = null;

        switch (day) {
            case Calendar.SUNDAY:
                days = "Minggu";
                break;
            case Calendar.MONDAY:
                days = "Senin";
                break;
            case Calendar.TUESDAY:
                days = "Selasa";
                break;
            case Calendar.WEDNESDAY:
                days = "Rabu";
                break;
            case Calendar.THURSDAY:
                days = "Kamis";
                break;
            case Calendar.FRIDAY:
                days = "Jumat";
                break;
            case Calendar.SATURDAY:
                days = "Sabtu";
                break;
        }


        switch (mont) {
            case Calendar.JANUARY:
                months = "Januari";
                break;
            case Calendar.FEBRUARY:
                months = "Februari";
                break;
            case Calendar.MARCH:
                months = "Maret";
                break;
            case Calendar.APRIL:
                months = "April";
                break;
            case Calendar.MAY:
                months = "Mei";
                break;
            case Calendar.JUNE:
                months = "Juni";
                break;
            case Calendar.JULY:
                months = "Juli";
                break;
            case Calendar.AUGUST:
                months = "Agustus";
                break;
            case Calendar.SEPTEMBER:
                months = "September";
                break;
            case Calendar.OCTOBER:
                months = "Oktober";
                break;
            case Calendar.NOVEMBER:
                months = "November";
                break;
            case Calendar.DECEMBER:
                months = "Desember";
                break;
        }

        HashMap<String, String> dates = new HashMap<>();
        dates.put("date", String.valueOf(date));
        dates.put("day", String.valueOf(days));
        dates.put("month", months);
        dates.put("year", String.valueOf(year));
        dates.put("hour", String.valueOf(hour));
        dates.put("minut", String.valueOf(minute));
        dates.put("second", String.valueOf(second));

        return dates;
    }

    public static String changeFormat(String dateTimes, String formatBefore, String formatAfter) {

        SimpleDateFormat fromdate = new SimpleDateFormat(formatBefore);
        SimpleDateFormat todate = new SimpleDateFormat(formatAfter);

        String reformatdate = "";

        try {
            reformatdate = todate.format(fromdate.parse(dateTimes));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reformatdate;

    }
}
