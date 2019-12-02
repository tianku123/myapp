package rh.study.knowledge.util;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by 18121254 on 2019/5/24.
 */
public class DateUtil {


    public static String yyyyMMddHHmmss = "yyyyMMdd HH:mm:ss";
    public static String yyyyMMddHHmm = "yyyyMMdd HH:mm";
    public static String yyyyMMddHH = "yyyyMMddHH";
    public static String yyyyMMddHH_Zh = "yyyy年MM月dd日HH时";
    public static String yyyyMMdd = "yyyyMMdd";
    public static String yyyyMM = "yyyyMM";

    public static String format(Object obj, String format) {
        if (obj == null) return null;
        try {
            Date date = (Date)obj;
            DateFormat dateFormat1 = new SimpleDateFormat(format);
            return dateFormat1.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDate(String obj, String format) {
        if (obj == null) return null;
        try {
            DateFormat dateFormat1 = new SimpleDateFormat(format);
            return dateFormat1.parse(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据日期计算所属周第一天日期和最后一天日期
     */
    public static String[] getWeekStartAndEndDate(String date) {
        try {
            Calendar calendar = new GregorianCalendar();
            DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
            Date date2 = dateFormat1.parse(date);
            calendar.clear();
            calendar.setTime(date2);


            String[] weekDates = new String[2];
            Date firstDateOfWeek, lastDateOfWeek;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 得到当天是这周的第几天
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if (dayOfWeek == 0) {
                calendar.add(Calendar.DAY_OF_WEEK, -6);
                firstDateOfWeek = calendar.getTime();
                weekDates[0] = sdf.format(firstDateOfWeek);
                weekDates[1] = date;
            } else {
                // 减去dayOfWeek,得到第一天的日期，因为Calendar用０－６代表一周七天，所以要减一
                calendar.add(Calendar.DAY_OF_WEEK, -(dayOfWeek - 1));
                firstDateOfWeek = calendar.getTime();
                // 每周7天，加６，得到最后一天的日子
                calendar.add(Calendar.DAY_OF_WEEK, 6);
                lastDateOfWeek = calendar.getTime();
                weekDates[0] = sdf.format(firstDateOfWeek);
                weekDates[1] = sdf.format(lastDateOfWeek);
            }


            return weekDates;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        System.out.println(hashAndMod("00025L2R9669L3R9719" , 10));
//        System.out.println(getPrntCateCd("00025#L2R9669#L3R9719"));
        String str = "A";
        System.out.println(str.toLowerCase());
        System.out.println(str);
    }

    public static String getPrntCateCd(String deptCategory){
        if (!StringUtils.isEmpty(deptCategory)) {
            String[] arr = deptCategory.split("#");
            if (arr.length == 2) {
                return "-";
            }
            if (arr.length > 2) {
                return arr[0] + arr[arr.length - 2];
            }
        }
        return null;
    }
    public static String hashAndMod(String value,int mode){
        if(null!=value){
            return Long.toString(floorMod(value.hashCode(),mode));
        }
        return "0";
    }
    public static long floorMod(long x, long y) {
        return x - floorDiv(x, y) * y;
    }
    public static long floorDiv(long x, long y) {
        long r = x / y;
        // if the signs are different and modulo not zero, round down
        if ((x ^ y) < 0 && (r * y != x)) {
            r--;
        }
        return r;
    }

    /**
     * 根据具体年份周数获取日期范围
     *
     * @param year
     * @param week
     * @return
     */
    public static String[] getWeekDays(int year, int week) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        // 设置每周的开始日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        String beginDate = sdf.format(cal.getTime());

        cal.add(Calendar.DAY_OF_WEEK, 6);
        String endDate = sdf.format(cal.getTime());

        return new String[]{beginDate, endDate};
    }

    public static String now() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        return format.format(new Date());
    }
}
