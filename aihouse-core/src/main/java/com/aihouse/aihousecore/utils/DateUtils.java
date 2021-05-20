package com.aihouse.aihousecore.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat formatMonth = new SimpleDateFormat("yyyy-MM");

    public DateUtils() {
    }

    public static String formatDate(Date date) {
        return date == null ? "" : dateFormat.format(date);
    }
    public static String formatDateTime(Date date) {
        return date == null ? "" : dateFormat1.format(date);
    }
    public static String formatMonth(Date date) {
        return formatMonth.format(date);
    }
    public static String formatDateByPattern(Date date, String pattern) {
        SimpleDateFormat dfs = new SimpleDateFormat(pattern);
        return dfs.format(date);
    }
    public static String returnZhiQian(Date createtime) throws ParseException {
        long now = System.currentTimeMillis();
        long overTime = (now - (createtime.getTime()))/1000;
        //设定时间日期
        SimpleDateFormat sdfTwo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long minute = overTime/60;
        long hour = overTime/3600;
        long day = hour/24;
        if(minute<60){
            if(minute>15&&minute<30){
                return "15分钟前";
            }else if(minute<60&&minute>30){
                return "30分钟前";
            }else{
                return minute+"分钟前";
            }
        }else if(hour<24){
            return hour+"小时前";
        }else if(day<365){
            if(day<7){
                return day+"天前";
            }else if(day < 30){
                return day/7+"周前";
            }else{
                return day/30+"月前";
            }
        }else{
            return day/365+"年前";
        }
    }
}
