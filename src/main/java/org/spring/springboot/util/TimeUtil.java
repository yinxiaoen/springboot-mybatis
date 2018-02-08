package org.spring.springboot.util;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by BookThief on 2016/6/8.
 */
public class TimeUtil {
    public static Integer remainSecondOfDay() {
        return 86400 - DateTime.now().getSecondOfDay();
    }

    /**
     * 返回当前时间的哗啦啦时间戳,格式yyyyMMddHHmmss
     * @return
     */
    public static Long currentTimeHllDT() {
        DateTime now = DateTime.now();
        return currentTimeHllDT(now);

    }

    /**
     * 返回当前时间的哗啦啦时间戳,格式yyyyMMdd
     * @return
     */
    public static Long currentTimeHllDT8() {
        DateTime now = DateTime.now();
        return currentTimeHllDT8(now);

    }

    /**
     * 返回当前时间的前后几天
     * @return
     */
    public static Long currentTimeHllDT8AfterDay(int offsetDay) {
        Calendar   c   =   Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, offsetDay);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String mDateTime=formatter.format(c.getTime());
        String strStart=mDateTime.substring(0, 8);//2007-10-24 09:30
        return Long.parseLong(strStart);

    }

    /**
     * 返回哗啦啦时间戳,格式yyyyMMddHHmmss
     * @param time joda时间
     * @return
     */
    public static Long currentTimeHllDT(DateTime time) {
        return time.getYear() * 10000000000l +
                time.getMonthOfYear() * 100000000l +
                time.getDayOfMonth() * 1000000l +
                time.getHourOfDay() * 10000l +
                time.getMinuteOfHour() * 100l +
                time.getSecondOfMinute();
    }

    /**
     * 返回哗啦啦时间戳,格式yyyyMMdd
     * @param time joda时间
     * @return
     */
    public static Long currentTimeHllDT8(DateTime time) {
        return time.getYear() * 10000l +
                time.getMonthOfYear() * 100l +
                time.getDayOfMonth() * 1l;
    }

    /**
     * 返回哗啦啦时间戳,格式yyyyMMddHHmmss
     * @param timestamp unix时间戳
     * @return
     */
    public static Long currentTimeHllDT(long timestamp) {
        DateTime time = new DateTime(timestamp);
        return currentTimeHllDT(time);
    }

    public static DateTime hllDT2DateTime(Long hllDT) {
        return new DateTime(
                (int)(hllDT/10000000000l),
                (int)((hllDT/100000000l)%100),
                (int)((hllDT/1000000l)%100),
                (int)((hllDT/10000l)%100),
                (int)((hllDT/100l)%100),
                (int)(hllDT%100)
        );
    }

    /**
     * 20160718121200 => '12:12'
     * @param hllDT
     * @return
     */
    public static String getFormattedTimeFromHllDT(Long hllDT) {
        Long time = hllDT%1000000;
        return String.format("%02d:%02d", time/10000, time%10000/100);
    }


    /**
     * 1315=>13:15
     * @param hllDT
     * @return
     */
    public static String getFormattedTimeFromLTOT(Long hllDT) {
        Long time = hllDT%1000000;
        return String.format("%02d:%02d", time/100, time%100);
    }
    /**
     * 20160718121200 => 1212
     * @param hllDT
     * @return
     */
    public static Long getDigitalTimeFromHllDT(Long hllDT) {
        Long time = hllDT%1000000;
        return time/100;
    }

    /**
     * 20160718121200 => 20160718
     * @param hllDT
     * @return
     */
    public static Long getDigitalDateFromHllDT(Long hllDT) {
        Long date = hllDT/1000000;
        return date;
    }

    /**
     * 20160718121200 => 2016-07-18 12:12
     * @param hllDT
     * @return
     */
    public static String getDigitalDateTimeFromHllDT(Long hllDT) {
        return String.format(
                "%04d-%02d-%02d %02d:%02d",
                hllDT / 10000000000L,
                hllDT % 10000000000L / 100000000,
                hllDT % 100000000 / 1000000,
                hllDT % 1000000 / 10000,
                hllDT % 10000 / 100
        );
    }
    /**
     * 获取当前时间 2016.07.18
     * @return
     */
    public static String getDigDateFromHllDT() {
        Long hllDT=currentTimeHllDT();
        Long date = hllDT/1000000;
        return String.format(
                "%04d.%02d.%02d",
                date / 10000L,
                date % 10000L / 100,
                date % 100 / 1
        );
    }

    /**
     * 返回当前年份yyyy
     * @return
     */
    public static Long getYear() {
        DateTime now = DateTime.now();
        return Long.valueOf(now.getYear());
    }

    /**
     * 获取当前日期的前后月
     * @param day
     * @return
     * @throws ParseException
     */
    public static Long getMonth(int day) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.add(Calendar.MONTH, day);
        Long monthDate = Long.valueOf(simpleDateFormat.format(calender.getTime()));
        return monthDate;
    }

    /**
     * 获取两个日期的间隔天数  20170101 20170102 -》1
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static long getDates(String date1,String date2 ) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = df.parse(date1);
        Date endDate = df.parse(date2);
        //获取两个日期之间的间隔天数
        long dates = TimeUtil.getDateInterval(beginDate, endDate);
        return dates;
    }


    /**
     * 获取两个日期的间隔天数  20170101 20170102 -》1
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static long getDatesBetweenMonth(String date1,String date2 ) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        Date beginDate = df.parse(date1);
        Date endDate = df.parse(date2);
        //获取两个日期之间的间隔天数
        long dates = TimeUtil.getDateInterval(beginDate, endDate);
        return dates;
    }

    /**
     * 获取两个日期之间的日期
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static List<Date> dateSplit(Date startDate, Date endDate) throws Exception {
        if (startDate.after(endDate)){
            throw new Exception("开始时间应该在结束时间之前");
        }
        Long spi = endDate.getTime() - startDate.getTime();
        Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数

        List<Date> dateList = new ArrayList<Date>();
        dateList.add(endDate);
        for (int i = 1; i <= step; i++) {
            dateList.add(new Date(dateList.get(i - 1).getTime()
                    - (24 * 60 * 60 * 1000)));// 比上一天减一
        }
        return dateList;
    }


    /**
     * 获取两个String（yyyymmdd）之间的所有日期，包括两个日期，返回的也是Long日期集合
     * @param startDateStr
     * @param endDateStr
     * @return
     * @throws Exception
     */
    public static List<Long> dateSplitToLong(String startDateStr, String endDateStr) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = df.parse(startDateStr);
        Date endDate = df.parse(endDateStr);
        if (beginDate.after(endDate)){
            throw new Exception("开始时间应该在结束时间之前");
        }
        Long spi = endDate.getTime() - beginDate.getTime();
        Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数

        List<Date> dateList = new ArrayList<Date>();
        dateList.add(endDate);
        for (int i = 1; i <= step; i++) {
            dateList.add(new Date(dateList.get(i - 1).getTime()
                    - (24 * 60 * 60 * 1000)));// 比上一天减一
        }
        List<Long> dateListLong = new ArrayList();
        for(Date date : dateList){{
            Long monthDate = Long.valueOf(df.format(date.getTime()));
            dateListLong.add(monthDate);
        }
        }
        return dateListLong;
    }



    /**
     * 将日期类型转成String类型
     * @param date
     * @param dataFomat
     * @return
     * @throws Exception
     */
    public static String DateToString(Date date,String dataFomat)throws Exception{

        SimpleDateFormat sdf = new SimpleDateFormat(dataFomat);

        String str = sdf.format(date);
        return str;
    }

    /**
     * 在指定日期加入天数
     * @param paramsDate
     * @param day
     * @return
     * @throws ParseException
     */
    public static String plusDay(String paramsDate,int day) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = sdf.parse(paramsDate);
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.DATE,day);
        return sdf.format(cl.getTime());
    }

    /**
     * 根据传入时间和N天，获取N天后的日期
     * @param voucherDate
     * @param num
     * @return
     * @throws ParseException
     */
    public static Long getAddNumDate(Long voucherDate,String num,String hour) throws ParseException {
        if (hour.length()==1){
            hour = "0"+hour;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(voucherDate.toString()));
        calendar.add(Calendar.DATE, Integer.parseInt(num));
        Long hopeCheckDate = Long.valueOf(sdf.format(calendar.getTime()).concat(hour));
        return hopeCheckDate;
    }

    /**
     * 根据开始日期和结束日期 获得间隔天数
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Long getDateInterval(Date beginDate,Date endDate){
        return (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24);
    }



    /**
     * 将日期转换成时间
     * 20170703 => 20170703240000
     * @param date
     * @return
     */
    public static Long getDateTime(Long date){
        return date*1000000+240000;
    }

    /**
     * 据开始时间和结束时间返回时间段内的时间集合
     */
    public static List getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List lDate = new ArrayList();
        lDate.add(beginDate);//把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        //使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        while (true) {
            //根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);//把结束时间加入集合
        return lDate;
    }

    /**
     * 获取时间戳（年月日时分秒毫秒）
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssS");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取月份的第一天
     * @param time yyyyMMdd
     * @return
     * @throws ParseException
     */
    public static String getFistMonthDay(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        Date date = sdf.parse(time);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取月份的最后一天
     * @param time yyyyMMdd
     * @return
     * @throws ParseException
     */
    public static String getLastMonthDay(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        Date date = sdf.parse(time);
        calendar.setTime(date);
        calendar.add(Calendar.MONDAY,1);
        calendar.set(Calendar.DAY_OF_MONTH,0);
        return sdf.format(calendar.getTime());
    }

    /**
     * 根据日期获取前一个月的日期
     * @param voucherDate
     * @return
     * @throws ParseException
     */
    public static Long getFrontMonthDayByTime(Long voucherDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(voucherDate.toString()));
        calendar.add(Calendar.MONTH, -1);
        Long hopeCheckDate = Long.valueOf(sdf.format(calendar.getTime()));
        return hopeCheckDate;
    }


    /**
     *
     * @param beforeDate <String>
     * @param afterDate  <String>
     * @return int
     * @throws ParseException
     */
    public static List<String> getMonthSpace(String beforeDate, String afterDate)
            throws ParseException {
        List<String> list =new ArrayList<String>();
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(sdf.parse(beforeDate));
        c2.setTime(sdf.parse(afterDate));
        Long beforeTime = c1.getTimeInMillis();
        while(beforeTime<(c2.getTimeInMillis())){
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMM");
            list.add(sd.format(c1.getTime()));
            c1.add(Calendar.MONTH, 1);
            beforeTime = c1.getTimeInMillis();
        }
        return list;

    }


}
