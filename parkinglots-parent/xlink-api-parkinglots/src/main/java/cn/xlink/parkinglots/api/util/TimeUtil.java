package cn.xlink.parkinglots.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TimeUtil {
    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return sdf.format(new Date());
    }
    //把utc字符串时间转换成long类型
    public static long getNumerical(String time){
//        time = time.replace("Z", " UTC");//UTC是本地时间
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d.getTime()/1000;
    }

    public static String getstring(long time){
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(time*1000);
        String str=format.format(date);
        return str;
    }

    //获取当天0点时间
    public static long putNowToday(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String dateString = format.format(date);

        try {
            date=format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time=date.getTime();
        return time;
    }
    //获取当月第一天0点时间
    public static long putNowMonth(int month){
        Date date=null;
        Calendar cale = null;
        cale = Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, month);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        String firstday = format.format(cale.getTime());
        try {
            date=format.parse(firstday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time=date.getTime();
        return time;
    }

    //获取当前月的最后一天
    public static long getNowMonthLastToday(){
        Date date=null;
        Calendar cale = null;
        cale = Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
//        String firstday = format.format(cale.getTime());
        try {
            date=format.parse(last);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time=date.getTime();
        return time;
    }

    //获取一个月的天数
    public static int getMonthDay(int month){
        Date date=null;
        Calendar cale = null;
        cale = Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, month);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        String firstday = format.format(cale.getTime());
        try {
            date=format.parse(firstday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**按时间降序排列**/
    public static List<String> sortListDesc(List<String> list) throws ParseException {
        List<String> retStr=new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map<Long,String> map = new TreeMap<Long,String>();
        for(int i=0;i<list.size();i++){
            String dateStr = list.get(i);
            map.put(sdf.parse(dateStr).getTime(), dateStr);
        }
        Collection<String> coll=map.values();
        retStr.addAll(coll);
        Collections.reverse(retStr);
        return retStr;
    }
}
