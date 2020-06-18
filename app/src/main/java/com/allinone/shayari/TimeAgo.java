package com.allinone.shayari;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeAgo {

    public static String covertTimeToText(String dataDate) {

        String convTime = null;

        String prefix = "";
        String suffix = "ago";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour   = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day  = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                convTime = second+" seconds "+suffix;
            } else if (minute < 60) {
                convTime = minute+" minutes "+suffix;
            } else if (hour < 24) {
                convTime = hour+" hours "+suffix;
            } else if (day >= 7) {
                if (day > 360) {
                    convTime = (day / 30) + " years " + suffix;
                } else if (day > 30) {
                    convTime = (day / 360) + " months " + suffix;
                } else {
                    convTime = (day / 7) + " week " + suffix;
                }
            } else if (day < 7) {
                convTime = day+" days "+suffix;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;
    }

}