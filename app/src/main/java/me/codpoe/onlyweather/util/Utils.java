package me.codpoe.onlyweather.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.codpoe.onlyweather.R;

/**
 * Created by Codpoe on 2016/5/14.
 */
public class Utils {

    // 日期转星期
    public static String dateToWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        String week = "";
        dayForWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayForWeek) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
        }
        return week;
    }

    // 通过星座名，返回星座图片 id
    public static int getConsImg(String consName) {
        switch (consName) {
            case "白羊座":
                return R.drawable.ic_bai_yang;
            case "金牛座":
                return R.drawable.ic_jin_niu;
            case "双子座":
                return R.drawable.ic_shuang_zi;
            case "巨蟹座":
                return R.drawable.ic_ju_xie;
            case "狮子座":
                return R.drawable.ic_shi_zi;
            case "处女座":
                return R.drawable.ic_chu_nv;
            case "天秤座":
                return R.drawable.ic_tian_ping;
            case "天蝎座":
                return R.drawable.ic_tian_xie;
            case "射手座":
                return R.drawable.ic_sagittarius;
            case "摩羯座":
                return R.drawable.ic_mo_jie;
            case "水瓶座":
                return R.drawable.ic_shui_ping;
            case "双鱼座":
                return R.drawable.ic_shuang_yu;
            default:
                return 0;

        }
    }

    // 正则表达式匹配百分数中的数字，例如：从 “75%” 中提取 “75”，并返回对应的星数
    public static float getRatingFromPercent(String percent) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(percent);
        if (m.find()) {
            return Float.valueOf(m.group()) / 20f;
        }
        return 2.5f;
    }

    // 正则表达式过滤高德地图定位获取的城市中的“市”，例如：从“广州市”中去掉“市”
    public static String getCityNameFromAMap(String cityName) {
        Pattern p = Pattern.compile("[\u5e02]");
        Matcher m = p.matcher(cityName);
        if (m.find()) {
            return m.replaceAll("");
        }
        return cityName;
    }
}
