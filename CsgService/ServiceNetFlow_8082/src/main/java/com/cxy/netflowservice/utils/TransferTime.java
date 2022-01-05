package com.cxy.netflowservice.utils;

public class TransferTime {

//
//    /**
//     * 转换毫秒数成“分、秒”，如“01:53”。若超过60分钟则显示“时、分、秒”，如“01:01:30
//     *
//     * @param 待转换的毫秒数
//     * */
//    public static String converLongTimeToStr(long time) {
//        int ss = 1000;
//        int mi = ss * 60;
//        int hh = mi * 60;
//
//        long hour = (time) / hh;
//        long minute = (time - hour * hh) / mi;
//        long second = (time - hour * hh - minute * mi) / ss;
//
//        String strHour = hour < 10 ? "0" + hour : "" + hour;
//        String strMinute = minute < 10 ? "0" + minute : "" + minute;
//        String strSecond = second < 10 ? "0" + second : "" + second;
//        if (hour > 0) {
//            return strHour + ":" + strMinute + ":" + strSecond;
//        } else {
//            return strMinute + ":" + strSecond;
//        }
//    }
//    /**
//     * 转换毫秒数成“分、秒”，如“01:53”。若超过60分钟则显示“时、分、秒”，如“01:01:30,若超过一天，则显示“2d12:33:33”
//     *
//     * @param 待转换的毫秒数
//     * */
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
