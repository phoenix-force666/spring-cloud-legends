package top.legendscloud.utils.mock.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public final class Dates {
	
	public static final String  SIMPLE_FORMAT = "yyyyMMddHHmmss";
	
	public static final String  YYYYMM_FORMATS = "yyyyMM";
	
	public static final String YYYYMM_FORMAT = "yyyymm";
	
	public static final String  SIMPLE_FORMAT_DAY = "yyyyMMdd";
	
	public static final String  SIMPLE_FORMAT_HOUR = "HHmmss";
	
	public static final String  SIMPLE_FORMAT_DAYS = "yyyy-MM-dd";

	public static String  formatNow(String pattern){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}
	
	/**
     * 返回日期String格式  yyyyMMddHHmmss
     * @param date
     * @return
     */
    public static String getDateToStrYyyyMMddhhmmss(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DateFormat format = new SimpleDateFormat(SIMPLE_FORMAT);
        return format.format(cal.getTime());
    }
	
	
	public static String formatDay(){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(SIMPLE_FORMAT_DAY));
	}
	
	public static String formatHour(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(SIMPLE_FORMAT_HOUR));
    }
	
	public static String formatDate(){
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(SIMPLE_FORMAT));
	}
	
	/**
	 * 对于yyyy-MM-dd HH:mm:ss转换yyyyMMddHHmmss
	 * @param date
	 * @return
	 */
	public static String converTime(String date){
		LocalDateTime time =LocalDateTime.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return time.format(DateTimeFormatter.ofPattern(SIMPLE_FORMAT));
	}
	
	
	/**
     * 对于yyyy-MM-dd HH:mm:ss转换yyyyMMddHHmmss
     * @param date
     * @return
     */
    public static String converDate(String date){
        SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
        try {
            Date dates = yyyymmdd.parse(date);
            SimpleDateFormat mat = new SimpleDateFormat("yyyy-MM-dd");
            return mat.format(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
	
    
    /**
     * 对于yyyy-MM-dd HH:mm:ss转换yyyyMMddHHmmss
     * @param date
     * @return
     */
    public static Date StringToDate(String date){
        SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
        try {
            Date dates = yyyymmdd.parse(date);
            return dates;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Date nextDayFirstDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }
    
    public static Long getSecsToEndOfCurrentDay(){

        Long secsOfNextDay  = nextDayFirstDate().getTime();
        //将当前时间转为毫秒数
        Long secsOfCurrentTime = new Date().getTime();
        //将时间转为秒数
        Long distance = (secsOfNextDay - secsOfCurrentTime)/1000;
        if (distance > 0 && distance != null){
            return distance;
        }

        return new Long(0);

    }
    
	public static void main(String[] args) {

	}
}
