package com.example.admin.tracer.Helper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by admin on 2016-08-17.
 */
public class AlramHelpler {
    private static long ts = System.currentTimeMillis();
    private static Date localTime = new Date(ts);
    private static SimpleDateFormat utcTolocal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static SimpleDateFormat outputFormate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
    private static String result;

            public static String calculateGoingTime (String utcTime){
                try {
                    int min = 60;
                    int hr = min * 60;
                    int day = hr * 24;
                    int week = day * 7;
                    int month = day * 30;
                    int year = month * 12;
                    utcTolocal.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date fDate = utcTolocal. parse(utcTime);
                    //String result = outputFormate.format(fDate).toString();
                    //Log.d("msg" , result);
                    Long goingTime = (localTime.getTime() - fDate.getTime())/1000;
                    if(goingTime < min) {
                        result = "방금";
                    } else if(goingTime>= min && goingTime<hr) {
                        goingTime = goingTime/min;
                        result = goingTime +"분";
                    } else if(goingTime>=hr && goingTime<day) {
                        goingTime = goingTime/hr;
                        result = goingTime + "시간";
                    } else if(goingTime >= day && goingTime<week) {
                        goingTime = goingTime/day;
                        result = goingTime + "일";
                    } else if(goingTime>=week && goingTime<month) {
                        goingTime = goingTime/week;
                        result = goingTime + "주";
                    } else if(goingTime>=month && goingTime< year) {
                        goingTime = goingTime/month;
                        result = goingTime + "달";
                         if(goingTime>=12) {
                            result = 1 + "년";
                        }
                    } else if(goingTime>=year && goingTime<year*6){
                        goingTime = goingTime/year;
                        result = goingTime + "년";
                    } else{
                        result = "몇년";
                    }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                return result;
    }
}
