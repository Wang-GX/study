package com.wgx.study.project.common.utils;
 
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * 参考：https://www.cnblogs.com/lingyejun/p/11298186.html
 */
public class CalendarAdjust {

    //默认取东八区(北京时间)，如果需要修改，请参考：CalendarAdjustGlobal
    private static final String TIME_ZONE = "GMT+08:00";
 
    /**
     * 获取指定某一天的开始时间戳CalendarAdjustGlobal
     *
     * @param timeStamp 毫秒级时间戳
     * @return
     */
    public static Long getDailyStartTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTimeInMillis(timeStamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
 
    /**
     * 获取指定某一天的结束时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return
     */
    public static Long getDailyEndTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTimeInMillis(timeStamp);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
 
    /**
     * 获取当月开始时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return
     */
    public static Long getMonthStartTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
 
    /**
     * 获取当月的结束时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return
     */
    public static Long getMonthEndTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));// 获取当前月最后一天
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
 
    /**
     * 获取当年的开始时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return
     */
    public static Long getYearStartTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTimeInMillis(timeStamp);
        calendar.add(Calendar.YEAR, 0);
        calendar.add(Calendar.DATE, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
 
    /**
     * 获取当年的最后时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @return
     */
    public static Long getYearEndTime(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTimeInMillis(timeStamp);
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTimeInMillis();
    }
 
    /**
     * 时间戳转字符串
     *
     * @param timestamp 毫秒级时间戳
     * @return TODO 该方法返回的日期时间格式为：2019-12-09T23:59:59.999，可以直接与MySQL中的datetime类型进行比较，可以结合MybatisPlus的条件查询使用替换掉一些以时间作为查询条件的SQL语句。
     */
    public static String timestampToStr(long timestamp) {
        ZoneId timezone = ZoneId.of(TIME_ZONE);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), timezone);
        return localDateTime.toString();
    }
 
    public static void main(String[] args) {
        Long currentTime = System.currentTimeMillis();
        System.out.println("Current Time : " + currentTime + " = " + timestampToStr(currentTime));
        Long dailyStart = getDailyStartTime(currentTime);
        Long dailyEnd = getDailyEndTime(currentTime);
        Long monthStart = getMonthStartTime(currentTime);
        Long monthEnd = getMonthEndTime(currentTime);
        Long yearStart = getYearStartTime(currentTime);
        Long yearEnd = getYearEndTime(currentTime);

        System.out.println("Daily Start : " + dailyStart + " = " + timestampToStr(dailyStart) + " Daily End : " + dailyEnd + " = " + timestampToStr(dailyEnd));
        System.out.println("Month Start : " + monthStart + " = " + timestampToStr(monthStart) + " Month End : " + monthEnd + " = " + timestampToStr(monthEnd));
        System.out.println("Year Start : " + yearStart + " = " + timestampToStr(yearStart) + " Year End : " + yearEnd + " = " + timestampToStr(yearEnd));
    }
}