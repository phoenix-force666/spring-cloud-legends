package com.legends.cloud.common.utils;

import com.legends.cloud.common.utils.date.DateStyle;
import com.legends.cloud.common.utils.date.Week;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class DateUtil {

    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

    private static final Object object = new Object();

    /**
     * Return 缺省的日期格式 (yyyy/MM/dd)
     *
     * @return 在页面中显示的日期格式
     */
    public static String getDatePattern() {
        return DateStyle.YYYY_MM_DD_HH_MM_SS.getValue();
    }


    /**
     * 减日期时间转为毫秒
     * @return
     */
    public static Date dateConvert(String dateTime){
         Date date=null;
        try {
            date=getDateFormat(DateStyle.YYYYMMDDHHMMSS.getValue()).parse(dateTime);
        } catch (ParseException e) {
           log.error("转换异常：{}",e);
        }
        return date;
    }

    /**
     * 减日期时间转为毫秒
     * @return
     */
    public static Date dateConvert_YYYYMMdd(String dateTime){
         Date date=null;
        try {
            date = getDateFormat(DateStyle.YYYYMMDD.getValue()).parse(dateTime);
        } catch (ParseException e) {
            log.error("ParseException: {}" , e);
        }
        return date;
    }


    public static long getTimestamp(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 12);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis() - System.currentTimeMillis();
    }

    /**
     * 减日期时间转为毫秒
     * @return
     */
    public static long dateConvertToms(String dateTime){
        return dateConvert(dateTime).getTime();
    }

    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param aMask
     *            输入字符串的格式
     * @param strDate
     *            一个按aMask格式排列的日期的字符串描述
     * @return Date 对象
     * @see SimpleDateFormat
     */
    public static final Date convertStringToDate(String aMask, String strDate){
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting :{} to date with mask :{}",strDate,aMask);
        }
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            log.error("ParseException: {}" , pe);
        }

        return (date);
    }


    /**
     * This method returns the current date in the format: yyyy-MM-dd
     *
     * @return the current date
     * @throws ParseException
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     *
     * @param aMask
     *            the date pattern the string is in
     * @param aDate
     *            a date object
     * @return a formatted string representation of the date
     *
     * @see SimpleDateFormat
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 根据日期格式，返回日期按datePattern格式转换后的字符串
     *
     * @param aDate
     * @return
     */
    public static final String convertDateToString(Date aDate) {
        return getDateTime(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue(), aDate);
    }

    /**
     * 根据日期格式，返回日期按dateTimePattern格式转换后的字符串
     *
     * @param aDate
     * @return
     */
    public static final String convertDateTimeToString(Date aDate) {
        return getDateTime(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue(), aDate);
    }

    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param strDate
     *            (格式 yyyyMMdd)
     * @return
     *
     * @throws ParseException
     */
    public static Date convertYYYYMMDDToDate(String strDate){
        Date aDate = null;
        if (log.isDebugEnabled()) {
            log.debug("converting date with pattern: {}" , DateStyle.YYYYMMDD.getValue());
        }
        aDate = convertStringToDate(DateStyle.YYYYMMDD.getValue(), strDate);
        return aDate;
    }



    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param strDate
     *            (格式 yyyy-MM-dd)
     * @return
     *
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate){
        Date aDate = null;

        if (log.isDebugEnabled()) {
            log.debug("converting date with pattern: {}" , DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
        }

        aDate = convertStringToDate(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue(), strDate);

        return aDate;
    }

    /**
     * 按照日期格式，将字符串解析为日期对象
     *
     * @param strDate
     *            (格式 yyyy-MM-dd HH:mm:ss)
     * @return
     *
     * @throws ParseException
     */
    public static Date convertTimeStringToDate(String strDate){
        Date aDate = null;

        if (log.isDebugEnabled()) {
            log.debug("converting date with pattern: {}" , DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
        }

        aDate = convertStringToDate(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue(), strDate);

        return aDate;
    }

    /**
     * 时间相加
     *
     * @param date
     * @param day
     * @return
     */
    public static Date dateAdd(Date date, int day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long dateDiffer(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / (1000 * 3600 * 24);
    }
    /**
     * 得到两个日期之间的天数差，包括开始和结束日期(如：beginCalender=2007-10-01，endCalendar=2007-10-20
     * 结果为：20)
     *
     * @param beginDay
     *            开始日期(小的)
     * @param endDay
     *            结束日期(大的)
     * @return
     */
    public static Long getDifferenceDays(Date beginDay, Date endDay) {
        Calendar beginCalender = Calendar.getInstance();
        beginCalender.setTime(beginDay);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDay);

        Long days = (endCalendar.getTimeInMillis() - beginCalender
                .getTimeInMillis())
                / (24 * 60 * 60 * 1000);
        days = days + 1;
        return days;
    }
    /**
     * 得到两个日期之间的天数差，包括开始和结束日期(如：beginCalender=2007-10-01，endCalendar=2007-10-20
     * 结果为：20)
     *
     * @param beginDate
     *            开始日期(小的)
     * @param endDate
     *            结束日期(大的)
     * @return
     */
    public static Long getDifferenceDaysByStringDate(String beginDate, String endDate) {
        SimpleDateFormat sdf = getDateFormat(DateStyle.YYYYMMDD.getValue());
        Date beginDay = null;
        Date endDay = null;
        try {
            beginDay = sdf.parse(beginDate);
            endDay = sdf.parse(endDate);
        } catch (ParseException e) {
            log.error("ParseException:{}",e);
        }
        Calendar beginCalender = Calendar.getInstance();
        beginCalender.setTime(beginDay);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDay);

        Long days = (endCalendar.getTimeInMillis() - beginCalender
                .getTimeInMillis())
                / (24 * 60 * 60 * 1000);
        return days;
    }
    public static Date getFirstDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date getLastDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
    /**
     * 返回本月最后一天
     * @param date
     * @return
     */
    public static String getLastDayStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,0);
        return getDateFormat(DateStyle.YYYYMMDD.getValue()).format(cal.getTime());
    }
    /**
     * 返回日期String格式  yyyyMMdd
     * @param date
     * @return
     */
    public static String getDateStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getDateFormat(DateStyle.YYYYMMDD.getValue()).format(cal.getTime());
    }

    /**
     * 返回日期String格式  自定义格式
     * @param date
     * @return
     */
    public static String getDateStrByFormat(Date date,String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DateFormat formatObj = new SimpleDateFormat(format);
        return formatObj.format(cal.getTime());
    }

    /**
     * 返回本月第一天
     * @param date  日期
     * @param month  0 当月  1下月  -1  上月  以此类推
     * @return
     */
    public static String getFirstDateStr(int month, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.set(Calendar.MONTH, month);
        return getDateFormat(DateStyle.YYYYMMDD.getValue()).format(cal.getTime());
    }

    /**
     * 得到当前时间,格式为yyyyMMddHHmmss
     *
     * @return
     */
    public static String getyyyyMMddHHmmssCurDate() {
        return getDateFormat(DateStyle.YYYYMMDDHHMMSS.getValue()).format(new Date());
    }
    /**
     * 得到当前时间,格式为yyyyMMddHHmmssSS
     *
     * @return
     */
    public static String getyyyyMMddHHmmssSSCurDate() {
        return getDateFormat(DateStyle.YYYYMMDDHHMMSSSS.getValue()).format(new Date());
    }
    /**
     * date转换成calendar，只精确到yyyyMMdd
     * @throws ParseException
     */
    public static Calendar getyyyyMMDDCalendar(Date date) throws ParseException{
        SimpleDateFormat dataFormat =getDateFormat(DateStyle.YYYYMMDD.getValue());
        dataFormat.parse(dataFormat.format(date));
        return dataFormat.getCalendar();
    }
    /**
     * 获取日期毫秒数(精确到天)
     * @throws ParseException
     */
    public static long getyyyyMMDDTimeInMillis(Date date) throws ParseException{
        return getyyyyMMDDCalendar(date).getTimeInMillis();
    }
    /**
     * 获取日期的HHmmss
     * @param date
     * @return
     */
    public static long getFormatHHmmss(Date date){
        return Long.valueOf( getDateFormat(DateStyle.HHMMSS.getValue()).format(date));
    }

    /**
     * 时间是否在<code>days</code>内
     *
     * @param old
     * @param days
     * @return
     */
    public static boolean isDaysInterval(Date old, int days) {
        Calendar now = Calendar.getInstance();
        return (now.getTimeInMillis() - old.getTime()) <= (1000L * 3600 * 24 * days);
    }

    /**
     * 得到当前日期后的N天的日期
     *
     * @param days
     *            天数
     * @return
     */
    public static Date getBackDaysOfNow(int days) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(now.getTimeInMillis() + 1000L * 3600 * 24 * days);
        return now.getTime();
    }

    public static Date getBackDaysOfDay(Date date,int days) {
        Calendar day = Calendar.getInstance();
        day.setTime(date);
        day.setTimeInMillis(day.getTimeInMillis() + 1000L * 3600 * 24 * days);
        return day.getTime();
    }

    public static Date getBeginOfDay(Date day) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(day);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
    }
    /**
     * 时间差比较
     * @param date 原时间点
     * @param diffSecond 时间差(秒)
     * @return
     */
    public static boolean dateTimeDiffCompare(Date date,int diffSecond){
        Date cur = new Date();
        long s = diffSecond*1000L;
        long diff = date.getTime()+s;
        long curTime = cur.getTime();
        if(curTime>diff){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 获取当前日期
     * @return
     */
    public static final String getCurrDateYYYYMMdd() {
        return getDateFormat(DateStyle.YYYYMMDD.getValue()).format(new Date());
    }

    /**
     * 获取昨日日期
     * @param dateTime
     * @return
     */
    public static final String getYesterDateYYYYMMdd(String dateTime) {
        Date date = dateConvert_YYYYMMdd(dateTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return  getDateFormat(DateStyle.YYYYMMDD.getValue()).format(calendar.getTime());
    }

    /**
     * 获取昨日日期
     * @return
     */
    public static final String getYesterDateYYYYMMdd() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return getDateFormat(DateStyle.YYYYMMDD.getValue()).format(calendar.getTime());
    }


    /**
     * 获取当前时间
     * @return
     */
    public static final String getCurrTimeHHmmss() {
        return getDateFormat(DateStyle.HHMMSS.getValue()).format(new Date());
    }

    /**
     * 获取当前时间
     * @param date
     * @return
     */
    public static final String getCurrTimeHHmmss(Date date) {
        return getDateFormat(DateStyle.HHMMSS.getValue()).format(date);
    }


    /**
     * 计算放款时间
     * @param dateTime
     * @return
     */
    public static final Date calculateInsteadPayDate(Date nowDate,String dateTime){
        String[] timeArray = dateTime.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(timeArray[2]));
        Date returnDate = nowDate;
        if(nowDate.getTime() > calendar.getTime().getTime()){
            calendar.add(Calendar.DAY_OF_MONTH, 1);  //加 一天
            returnDate = calendar.getTime();
        }
        return returnDate;
    }

    /**
     * 计算任小贷还款时间
     * @param nowDate
     * @param days
     * @return
     */
    public static final String calculateRepaymentDate(Date nowDate,int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return getDateStr(calendar.getTime());
    }

    /**
     * 任分期计算还回还款时间
     * @param nowDate
     * @param months
     * @param dateTime
     * @return
     */
    public static final String rfqcalculateRepaymentDate(Date nowDate,int months,String dateTime){
        int days =0;
        String[] timeArray = dateTime.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(timeArray[2]));
        calendar.add(Calendar.MONTH, months);
        if(nowDate.getTime() > calendar.getTime().getTime()){
            days = 1; //加 一天
        }
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return getDateStr(calendar.getTime());
    }


    /**
     * 返回明天的日期
     * @return
     */
    public static final Date getTomorrowDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 返回明日日期
     * @return
     */
    public static final String getTomorrowDate_YYYYMMDD(){
        Date firstDay = getTomorrowDate();
        return  getDateFormat(DateStyle.YYYYMMDD.getValue()).format(firstDay);
    }

    /**
     *
     * @param date
     * @return
     */
    public static final String getDateStr(String date){
        return date.substring(0, 4) + "年" + date.substring(4,6) + "月" + date.substring(6);
    }
    /**
     * 后去现在时间前多少秒时间
     * @param BeforeSecond
     * @return String
     */
    public static final String getNowDateByBeforeSecond(int BeforeSecond){
        Calendar c = new GregorianCalendar();
        Date date = new Date();
        c.setTime(date);
        c.add(Calendar.SECOND,BeforeSecond);
        date=c.getTime();
        return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()).format(date);
    }
    /**
     * 后去现在时间前多少秒时间
     * @param BeforeSecond
     * @return String
     */
    public static final Date getNowDateByBeforeSecond1(int BeforeSecond){
        Calendar c = new GregorianCalendar();
        Date date = new Date();
        c.setTime(date);
        c.add(Calendar.SECOND,BeforeSecond);
        return c.getTime();
    }
    /**
     * 在日期上增加数个整月
     */
    public static String addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return  getDateFormat(DateStyle.YYYYMMDD.getValue()).format(cal.getTime());
    }

    public static Date setHHmmss(int hour,int minute,int second,Date curDate){
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(curDate);
        startCal.set(Calendar.HOUR_OF_DAY, hour);
        startCal.set(Calendar.MINUTE, minute);
        startCal.set(Calendar.SECOND, second);
        return startCal.getTime();
    }

    /**
     * 返回日期String格式  yyyyMMdd
     * @param date
     * @return
     */
    public static String getHHmmss(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return  getDateFormat(DateStyle.HHMMSS.getValue()).format(cal.getTime());
    }




    /**
     * 获取SimpleDateFormat
     *
     * @param pattern
     *            日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException
     *             异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    /**
     * 获取日期中的某数值。如获取月份
     *
     * @param date
     *            日期
     * @param dateType
     *            日期格式
     * @return 数值
     */
    private static int getInteger(Date date, int dateType) {
        int num = 0;
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            num = calendar.get(dateType);
        }
        return num;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date
     *            日期字符串
     * @param dateType
     *            类型
     * @param amount
     *            数值
     * @return 计算后日期字符串
     */
    private static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            myDate = addInteger(myDate, dateType, amount);
            dateString = DateToString(myDate, dateStyle);
        }
        return dateString;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date
     *            日期
     * @param dateType
     *            类型
     * @param amount
     *            数值
     * @return 计算后日期
     */
    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 获取精确的日期
     *
     * @param timestamps
     *            时间long集合
     * @return 日期
     */
    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<Long, long[]>();
        List<Long> absoluteValues = new ArrayList<Long>();

        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = { timestamps.get(i), timestamps.get(j) };
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的。此时minAbsoluteValue为0
                // 因此不能将minAbsoluteValue取默认值0
                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    minAbsoluteValue = absoluteValues.get(0);
                    for (int i = 1; i < absoluteValues.size(); i++) {
                        if (minAbsoluteValue > absoluteValues.get(i)) {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);

                    long dateOne = timestampsLastTmp[0];
                    long dateTwo = timestampsLastTmp[1];
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne : dateTwo;
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }

    /**
     * 判断字符串是否为日期字符串
     *
     * @param date
     *            日期字符串
     * @return true or false
     */
    public static boolean isDate(String date) {
        boolean isDate = false;
        if (date != null) {
            if (getDateStyle(date) != null) {
                isDate = true;
            }
        }
        return isDate;
    }

    /**
     * 获取日期字符串的日期风格。失敗返回null。
     *
     * @param date
     *            日期字符串
     * @return 日期风格
     */
    public static DateStyle getDateStyle(String date) {
        DateStyle dateStyle = null;
        Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
        List<Long> timestamps = new ArrayList<Long>();
        for (DateStyle style : DateStyle.values()) {
            if (style.isShowOnly()) {
                continue;
            }
            Date dateTmp = null;
            if (date != null) {
                try {
                    ParsePosition pos = new ParsePosition(0);
                    dateTmp = getDateFormat(style.getValue()).parse(date, pos);
                    if (pos.getIndex() != date.length()) {
                        dateTmp = null;
                    }
                } catch (Exception e) {
                }
            }
            if (dateTmp != null) {
                timestamps.add(dateTmp.getTime());
                map.put(dateTmp.getTime(), style);
            }
        }
        Date accurateDate = getAccurateDate(timestamps);
        if (accurateDate != null) {
            dateStyle = map.get(accurateDate.getTime());
        }
        return dateStyle;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date
     *            日期字符串
     * @return 日期
     */
    public static Date StringToDate(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return StringToDate(date, dateStyle);
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return 日期
     */
    public static Date StringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception e) {
            }
        }
        return myDate;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date
     *            日期字符串
     * @param dateStyle
     *            日期风格
     * @return 日期
     */
    public static Date StringToDate(String date, DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle != null) {
            myDate = StringToDate(date, dateStyle.getValue());
        }
        return myDate;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date
     *            日期
     * @param pattern
     *            日期格式
     * @return 日期字符串
     */
    public static String DateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date
     *            日期
     * @param dateStyle
     *            日期风格
     * @return 日期字符串
     */
    public static String DateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = DateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date
     *            旧日期字符串
     * @param newPattern
     *            新日期格式
     * @return 新日期字符串
     */
    public static String StringToString(String date, String newPattern) {
        DateStyle oldDateStyle = getDateStyle(date);
        return StringToString(date, oldDateStyle, newPattern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date
     *            旧日期字符串
     * @param newDateStyle
     *            新日期风格
     * @return 新日期字符串
     */
    public static String StringToString(String date, DateStyle newDateStyle) {
        DateStyle oldDateStyle = getDateStyle(date);
        return StringToString(date, oldDateStyle, newDateStyle);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date
     *            旧日期字符串
     * @param olddPattern
     *            旧日期格式
     * @param newPattern
     *            新日期格式
     * @return 新日期字符串
     */
    public static String StringToString(String date, String olddPattern, String newPattern) {
        return DateToString(StringToDate(date, olddPattern), newPattern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date
     *            旧日期字符串
     * @param olddDteStyle
     *            旧日期风格
     * @param newParttern
     *            新日期格式
     * @return 新日期字符串
     */
    public static String StringToString(String date, DateStyle olddDteStyle, String newParttern) {
        String dateString = null;
        if (olddDteStyle != null) {
            dateString = StringToString(date, olddDteStyle.getValue(), newParttern);
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date
     *            旧日期字符串
     * @param olddPattern
     *            旧日期格式
     * @param newDateStyle
     *            新日期风格
     * @return 新日期字符串
     */
    public static String StringToString(String date, String olddPattern, DateStyle newDateStyle) {
        String dateString = null;
        if (newDateStyle != null) {
            dateString = StringToString(date, olddPattern, newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date
     *            旧日期字符串
     * @param olddDteStyle
     *            旧日期风格
     * @param newDateStyle
     *            新日期风格
     * @return 新日期字符串
     */
    public static String StringToString(String date, DateStyle olddDteStyle, DateStyle newDateStyle) {
        String dateString = null;
        if (olddDteStyle != null && newDateStyle != null) {
            dateString = StringToString(date, olddDteStyle.getValue(), newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date
     *            日期
     * @param yearAmount
     *            增加数量。可为负数
     * @return 增加年份后的日期字符串
     */
    public static String addYear(String date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date
     *            日期
     * @param yearAmount
     *            增加数量。可为负数
     * @return 增加年份后的日期
     */
    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date
     *            日期
     * @param monthAmount
     *            增加数量。可为负数
     * @return 增加月份后的日期字符串
     */
    public static String addMonth(String date, int monthAmount) {
        return addInteger(date, Calendar.MONTH, monthAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date
     *            日期字符串
     * @param dayAmount
     *            增加数量。可为负数
     * @return 增加天数后的日期字符串
     */
    public static String addDay(String date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date
     *            日期
     * @param dayAmount
     *            增加数量。可为负数
     * @return 增加天数后的日期
     */
    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     *
     * @param date
     *            日期字符串
     * @param hourAmount
     *            增加数量。可为负数
     * @return 增加小时后的日期字符串
     */
    public static String addHour(String date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     *
     * @param date
     *            日期
     * @param hourAmount
     *            增加数量。可为负数
     * @return 增加小时后的日期
     */
    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     *
     * @param date
     *            日期字符串
     * @param minuteAmount
     *            增加数量。可为负数
     * @return 增加分钟后的日期字符串
     */
    public static String addMinute(String date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     *
     * @param date
     *            日期
     *            增加数量。可为负数
     * @return 增加分钟后的日期
     */
    public static Date addMinute(Date date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     *
     * @param date
     *            日期字符串
     *            增加数量。可为负数
     * @return 增加秒钟后的日期字符串
     */
    public static String addSecond(String date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     *
     * @param date
     *            日期
     *            增加数量。可为负数
     * @return 增加秒钟后的日期
     */
    public static Date addSecond(Date date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date
     *            日期字符串
     * @return 年份
     */
    public static int getYear(String date) {
        return getYear(StringToDate(date));
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date
     *            日期
     * @return 年份
     */
    public static int getYear(Date date) {
        return getInteger(date, Calendar.YEAR);
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date
     *            日期字符串
     * @return 月份
     */
    public static int getMonth(String date) {
        return getMonth(StringToDate(date));
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date
     *            日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        return getInteger(date, Calendar.MONTH) + 1;
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date
     *            日期字符串
     * @return 天
     */
    public static int getDay(String date) {
        return getDay(StringToDate(date));
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date
     *            日期
     * @return 天
     */
    public static int getDay(Date date) {
        return getInteger(date, Calendar.DATE);
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date
     *            日期字符串
     * @return 小时
     */
    public static int getHour(String date) {
        return getHour(StringToDate(date));
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date
     *            日期
     * @return 小时
     */
    public static int getHour(Date date) {
        return getInteger(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date
     *            日期字符串
     * @return 分钟
     */
    public static int getMinute(String date) {
        return getMinute(StringToDate(date));
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date
     *            日期
     * @return 分钟
     */
    public static int getMinute(Date date) {
        return getInteger(date, Calendar.MINUTE);
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date
     *            日期字符串
     * @return 秒钟
     */
    public static int getSecond(String date) {
        return getSecond(StringToDate(date));
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date
     *            日期
     * @return 秒钟
     */
    public static int getSecond(Date date) {
        return getInteger(date, Calendar.SECOND);
    }

    /**
     * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date
     *            日期字符串
     * @return 日期
     */
    public static String getDate(String date) {
        return StringToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 获取日期。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date
     *            日期
     * @return 日期
     */
    public static String getDate(Date date) {
        return DateToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date
     *            日期字符串
     * @return 时间
     */
    public static String getTime(String date) {
        return StringToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date
     *            日期
     * @return 时间
     */
    public static String getTime(Date date) {
        return DateToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 获取日期的时间。默认yyyy-MM-dd HH:mm:ss格式。失败返回null。
     *
     * @param date
     *            日期字符串
     * @return 时间
     */
    public static String getDateTime(String date) {
        return StringToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取日期的时间。默认yyyy-MM-dd HH:mm:ss格式。失败返回null。
     *
     * @param date
     *            日期
     * @return 时间
     */
    public static String getDateTime(Date date) {
        return DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取日期的时间。DateStyle
     *
     * @param date
     *            日期字符串
     * @return 时间
     */
    public static String getDateTime(Date date,DateStyle dateStyle) {
        return DateToString(date, dateStyle);
    }

    /**
     * 获取日期的星期。失败返回null。
     *
     * @param date
     *            日期字符串
     * @return 星期
     */
    public static Week getWeek(String date) {
        Week week = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            week = getWeek(myDate);
        }
        return week;
    }

    /**
     * 获取日期的星期。失败返回null。
     *
     * @param date
     *            日期
     * @return 星期
     */
    public static Week getWeek(Date date) {
        Week week = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (weekNumber) {
            case 0:
                week = Week.SUNDAY;
                break;
            case 1:
                week = Week.MONDAY;
                break;
            case 2:
                week = Week.TUESDAY;
                break;
            case 3:
                week = Week.WEDNESDAY;
                break;
            case 4:
                week = Week.THURSDAY;
                break;
            case 5:
                week = Week.FRIDAY;
                break;
            case 6:
                week = Week.SATURDAY;
                break;
        }
        return week;
    }

    /**
     * 获取两个日期相差的天数
     *
     * @param date
     *            日期字符串
     * @param otherDate
     *            另一个日期字符串
     * @return 相差天数。如果失败则返回-1
     */
    public static int getIntervalDays(String date, String otherDate) {
        return getIntervalDays(StringToDate(date), StringToDate(otherDate));
    }

    /**
     * @param date
     *            日期
     * @param otherDate
     *            另一个日期
     * @return 相差天数。如果失败则返回-1
     */
    public static int getIntervalDays(Date date, Date otherDate) {
        int num = -1;
        Date dateTmp = DateUtil.StringToDate(DateUtil.getDate(date), DateStyle.YYYY_MM_DD);
        Date otherDateTmp = DateUtil.StringToDate(DateUtil.getDate(otherDate), DateStyle.YYYY_MM_DD);
        if (dateTmp != null && otherDateTmp != null) {
            long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
            num = (int) (time / (24 * 60 * 60 * 1000));
        }
        return num;
    }

    /**
     * 获取期间的年龄
     *
     * @param date
     * @param otherDate
     * @return
     *
     * 		2014-12-2 下午06:45:02 段
     *
     * @return String
     */
    public static String getAge(Date date, Date otherDate) {
        int dis = DateUtil.getIntervalDays(new Date(), otherDate);
        int year = dis / 365;
        int month = dis % 365 / 30;
        int day = dis % 365 % 31;
        String age = (year > 0 ? year + "岁" : "") + (month > 0 ? month + "个月" : "") + (day + "天");
        return age;
    }

    /**
     * @Author herion
     * @Description 获取时区时间
     * @Date  2019/3/1
     * @Param [timeZoneOffset]
     * @return java.lang.String
     **/
    public static String getFormatedDateString(float timeZoneOffset){
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }

        int newTime=(int)(timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }


}
