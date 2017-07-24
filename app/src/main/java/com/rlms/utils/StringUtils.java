package com.rlms.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class StringUtils {

    public static String getConvertedDate(long dateInLong){

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(dateInLong);
        Date d = (Date) c.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(d);//this variable time contains the time in the format of "day/month/year".

    }

    public static String getDateInStringFormat(){
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        return df.format(today);

    }


    public static String parseDateToddMMyyyy(String time) {

//        12-May-2017 12:55:23 PM

        String inputPattern = "EEE MMM dd HH:mm:ss z yyyy";
        String outputPattern = "dd-MMM-yyyy h:mm:ss a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}
