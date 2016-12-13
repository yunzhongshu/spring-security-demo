/*
 * @(#) MyDateUtil.java 2015年7月14日 Copyright 2010 NetEase.com, Inc. All rights reserved.
 */
package com.zyy.scenery.common.util;

import org.apache.commons.lang.StringUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 类 <code>MyDateUtil</code>
 *
 * @author zhangyunyun
 * @version 2015年7月14日
 */
public class MyDateUtil {

    public static final String Y_M_D     = "yyyy-MM-dd";
    public static final String Y_M_D_HM  = "yyyy-MM-dd HH:mm";
    public static final String Y_M_D_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD       = "yyyyMMdd";
    public static final String YMDHM     = "yyyyMMddHHmm";
    public static final String YMDHMS    = "yyyyMMddHHmmss";
    public static final String ymd       = "yyyy/MM/dd";
    public static final String ymd_HM    = "yyyy/MM/dd HH:mm";
    public static final String ymd_HMS   = "yyyy/MM/dd HH:mm:ss";

    /**
     * 智能转换日期
     * 
     * @param date
     * @return
     */
    public static String smartFormat(java.util.Date date) {
        String dateStr = null;
        if (date == null) {
            dateStr = "";
        } else {
            try {
                dateStr = formatDate(date, Y_M_D_HMS);
                // 时分秒
                if (dateStr.endsWith(" 00:00:00")) {
                    dateStr = dateStr.substring(0, 10);
                }
                // 时分
                else if (dateStr.endsWith("00:00")) {
                    dateStr = dateStr.substring(0, 16);
                }
                // 秒
                else if (dateStr.endsWith(":00")) {
                    dateStr = dateStr.substring(0, 16);
                }
            } catch (Exception ex) {
                throw new IllegalArgumentException("转换日期失败: " + ex.getMessage(), ex);
            }
        }
        return dateStr;
    }

    /**
     * 智能转换日期
     * 
     * @param text
     * @return
     */
    public static java.util.Date smartFormat(String text) {
        java.util.Date date = null;
        try {
            if (text == null || text.length() == 0) {
                date = null;
            } else if (text.length() == 10) {
                date = formatStringToDate(text, Y_M_D);
            } else if (text.length() == 13) {
                date = new Date(Long.parseLong(text));
            } else if (text.length() == 16) {
                date = formatStringToDate(text, Y_M_D_HM);
            } else if (text.length() == 19) {
                date = formatStringToDate(text, Y_M_D_HMS);
            } else {
                throw new IllegalArgumentException("日期长度不符合要求!");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("日期转换失败!");
        }
        return date;
    }

    /**
     * 格式化日期格式
     * 
     * @param argDate
     * @param argFormat
     * @return 格式化后的日期字符串
     */
    public static String formatDate(java.util.Date argDate, String argFormat) throws Exception {
        if (argDate == null) {
            throw new Exception("参数[日期]不能为空!");
        }
        if (StringUtils.isEmpty(argFormat)) {
            argFormat = Y_M_D;
        }
        SimpleDateFormat sdfFrom = new SimpleDateFormat(argFormat);
        return sdfFrom.format(argDate).toString();
    }

    /**
     * 把字符串格式化成日期
     * 
     * @param argDateStr
     * @param argFormat
     * @return
     */
    public static java.util.Date formatStringToDate(String argDateStr, String argFormat) throws Exception {
        if (argDateStr == null || argDateStr.trim().length() < 1) {
            throw new Exception("参数[日期]不能为空!");
        }
        String strFormat = argFormat;
        if (StringUtils.isEmpty(strFormat)) {
            strFormat = Y_M_D;
            if (argDateStr.length() > 16) {
                strFormat = Y_M_D_HMS;
            } else if (argDateStr.length() > 10) {
                strFormat = Y_M_D_HM;
            }
        }
        SimpleDateFormat sdfFormat = new SimpleDateFormat(strFormat);
        // 严格模式
        sdfFormat.setLenient(false);
        try {
            return sdfFormat.parse(argDateStr);
        } catch (ParseException e) {
            throw new Exception(e);
        }
    }

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getTimestamp(int year, int month, int day, int hour, int min, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, min, second);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp getTimestampBeforeMs(long someMs) {
        return new Timestamp(System.currentTimeMillis() - someMs);
    }

    public static Date getCurrentSqlDate() {
        return new Date(System.currentTimeMillis());
    }

    public static Date getSqlDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0);
        return new Date(calendar.getTimeInMillis());

    }

    public static Timestamp getBeginTimeOfDay(Timestamp timestamp) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static java.util.Date getBeginDateOfDay(java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    public static Timestamp getBeginTimeOfMonth(Timestamp timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Timestamp getEndTimeOfMonth(Timestamp timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取一个时间的月份
     * @param timestamp
     * @return
     */
    public static int getMonthOfTime(Timestamp timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        return calendar.get(Calendar.MONTH) + 1;
    }
    public static  int getDayOfTime(Timestamp timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }


    public static java.util.Date convertToDate(Timestamp timestamp){
        return new java.util.Date(timestamp.getTime());
    }

    public static Timestamp convertToTimestamp(java.util.Date date){
        if(date == null)
            return null;
        return new Timestamp(date.getTime());
    }

    public static void main(String[] args) {
        // System.out.println(MyDateUtil.getTimestamp(2015, 07, 28, 14, 0, 0));
//        List<TimeSplitVO> list = getTimeSplitMonths(getCurrentTimestamp(), getCurrentTimestamp());
//        for(TimeSplitVO timeSplitVO : list){
//            System.out.println(timeSplitVO.getBeginTime());
//            System.out.println(timeSplitVO.getEndTime());
//        }
        System.out.println(getTimestampBeforeMs(-org.apache.commons.lang.time.DateUtils.MILLIS_PER_DAY * 6));
    }
}
