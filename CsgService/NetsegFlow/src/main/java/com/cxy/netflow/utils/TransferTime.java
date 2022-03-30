package com.cxy.netflow.utils;

public class TransferTime {

    public static String converLongTimeToStr(long time) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        Long day = time / dd;
        Long hour = (time - day * dd) / hh;
        Long minute = (time - day * dd - hour * hh) / mi;
        Long second = (time - day * dd - hour * hh - minute * mi) / ss;


        String strDay = day + "d";
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;

        if(day > 0) {
            return strDay + strHour + ":" + strMinute + ":" + strSecond;
        }else if(hour > 0) {
            return strHour + ":" + strMinute + ":" + strSecond;
        }else {
            return strMinute + ":" + strSecond;
        }

    }

}
