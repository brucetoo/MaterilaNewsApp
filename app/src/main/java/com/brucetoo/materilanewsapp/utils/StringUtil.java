package com.brucetoo.materilanewsapp.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Bruce Too
 * On 4/21/15.
 * At 16:10
 */
public class StringUtil {
    public static StringBuffer getBuffer() {
        return new StringBuffer();
    }

    public static String subString(String str, int num) {
        if (StringUtil.isEmpty(str))
            return null;
        int len = str.length();

        if (len > num)
            return str.substring(0, num);
        return str;
    }

    /**
     * 判断是否是 电话号码
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "^//(?(//d{3})//)?[- ]?(//d{3})[- ]?(//d{5})$";
        String expression2 = "^//(?(//d{3})//)?[- ]?(//d{4})[- ]?(//d{4})$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        Pattern pattern2 = Pattern.compile(expression2);
        Matcher matcher2 = pattern2.matcher(inputStr);
        if (matcher.matches() || matcher2.matches()) {
            isValid = true;
        }
        return isValid;

    }

    /**
     * 判断IP地址是否有效
     *
     * @param IPAddress
     * @return
     */
    public static boolean isIPAddressValid(String IPAddress) {
        boolean isValid = false;
        Pattern pattern = Pattern
                .compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher matcher = pattern.matcher(IPAddress);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;

    }

    /**
     * 判断字符串的编码
     *
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    public static String getRandChar(String source) {
        if (StringUtil.isEmpty(source))
            return "";
        int length = source.length();
        Random rand = new Random();
        char[] chars = source.toCharArray();
        int index = rand.nextInt(length);
        return chars[index] + "";
    }

    /**
     * 两日期相减
     *
     */
    public static long diffDate(int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        c.add(Calendar.DAY_OF_MONTH, (0 - num));
        return c.getTime().getTime();
    }

    public static int toInt(String num) {
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            return 0;
        }
    }

    public static long toLong(String num) {
        try {
            return Long.parseLong(num);
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean toBoolean(String num) {
        if (isEmpty(num))
            return false;
        if (num.equals("true"))
            return true;
        return false;
    }

    public static String getStrDate(String longDate) {
        if (isEmpty(longDate))
            return "";
        long time = Long.parseLong(longDate);
        Date date = new Date(time);
        return getStrDate(date, "MM-dd HH:mm");
    }

    public static String getStrDate(String longDate, String format) {
        return isEmpty(longDate) ? "" : getStrDate(new Date(Long.parseLong(longDate)), format);
    }

    public static String getStrDate(long time, String format) {
        Date date = new Date(time);
        return getStrDate(date, format);
    }

    public static Date getDate(String longDate) {
        if (isEmpty(longDate))
            return null;
        long time = Long.parseLong(longDate);
        return new Date(time);
    }

    public static String[] getData(String str) {
        String empty[] = { "", "" };
        if (isEmpty(str))
            return empty;
        try {
            int start = str.indexOf(":");
            if (start > 0 && start < str.length()) {
                empty[0] = str.substring(0, start);
                empty[1] = str.substring(start + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return empty;
    }

    public static final String ENCODING = "utf-8";

    private StringUtil() {
    }

    public static String getStrDate() {
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
        return dd.format(new Date());
    }

    public static String getStrDate(Date date) {
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
        return dd.format(date);
    }

    public static String getStrDate(Date date, String formate) {
        return new SimpleDateFormat(formate).format(date);
    }

    /**
     * 根据生日计算年龄
     *
     * @param birthday
     *            生日字符串（如： 1907-01-01）
     * @return 年龄
     */
    public static int getAgeByBirthday(String birthday) {

        if (TextUtils.isEmpty(birthday))
            return 115;

        int age = 115;
        Date date;
        Calendar cal = Calendar.getInstance();
        Calendar curCal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

        try {

            date = df.parse(birthday.trim());
            cal.setTime(date);

            age = curCal.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
            age -= (curCal.get(Calendar.MONTH) >= cal.get(Calendar.MONTH) && curCal
                    .get(Calendar.DAY_OF_MONTH) >= cal
                    .get(Calendar.DAY_OF_MONTH)) ? 0 : 1;

            if (age < 0)
                age = 0;
        } catch (ParseException e) {

            e.printStackTrace();
        }

        return age;
    }

    public static String encode(String data) {
        try {
            return URLEncoder.encode(data, ENCODING);
        } catch (Exception e) {
            return "";
        }
    }

    public static String decode(String data) {
        try {
            return URLDecoder.decode(data, ENCODING);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    // 随机数字产生
    public static String getRandomStr(int num) {
        StringBuffer temp = new StringBuffer();
        Random rand = new Random();
        for (int i = 0; i < num; i++) {
            temp.append(rand.nextInt(10));
        }
        return temp.toString();
    }

    /**
     * 判断指定的字符串是否为null或空字符串
     *
     * @param str
     * @return
     */
    public static final boolean isNullOrEmpty(String str) {
        if (str == null || "".equals(str) || "null".equals(str.trim())) {
            return true;
        }

        return false;
    }

    /**
     * 判断指定的字符串是否为null、空字符串或空格字符串
     *
     * @param str
     * @return
     */
    public static final boolean isNullOrTrimEmpty(String str) {
        if ((str == null) || "".equals(str.trim())) {
            return true;
        }

        return false;
    }

    /**
     * 判断指定的字符串不为null，空字符串
     *
     * @param str
     * @return
     */
    public static final boolean isNotNullOrEmpty(String str) {
        if ((str == null) || "".equals(str) || "null".equals(str)) {
            return false;
        }

        return true;
    }

    public static String toStrNum(int num, int n) {
        String pat = "";
        for (int i = 0; i < n; i++)
            pat = pat + "0";
        java.text.DecimalFormat format = new java.text.DecimalFormat(pat);
        return format.format(num);
    }

    public static String filterChar(String str, String replace) {
        if (isEmpty(str))
            return "";
        str = str.replace("\\", replace);
        str = str.replace("\"", replace);
        str = str.replace("\'", replace);
        String reg = "@'\"？`~!@#$%^&*()_+={[}]|\\:;<>.?/-Aa%！￥……（）、：；“”’‘《》，。？－";
        for (int i = 0; i < reg.length(); i++) {
            str = str.replace(reg.substring(i, i + 1), replace);
        }

        return str;
    }

    public static String urlDecode(String str) {
        if (isEmpty(str))
            return "";
        return str.replace("&amp;", "&");
    }

    public static boolean isMobile(String str) {
        if (str == null || str.equals("") || str.trim().equals(""))
            return false;

        if (str.length() == 11 && isNum(str)) {
            if (str.startsWith("1"))
                return true;
        }

        return false;
    }

    public static boolean isNum(String str) {
        for (int i = str.length(); --i >= 0;)
            if (!Character.isDigit(str.charAt(i)))
                return false;
        return true;
    }

    public static boolean isEmpty(String str) {
        if (str == null || str == "" || str.trim().equals("") || str.trim().equalsIgnoreCase("null") || str.trim().length() == 0)
            return true;
        else
            return false;
    }

    public static boolean isNotEmptyAndNum(String str) {
        if (isEmpty(str)) {
            return false;
        }
        for (int i = str.length(); --i >= 0;)
            if (!Character.isDigit(str.charAt(i)))
                return false;
        return true;
    }

    public static int getPixWidth(String str, Paint paint) {
        Rect rect = new Rect();

        // 返回包围整个字符串的最小的一个Rect区域
        paint.getTextBounds(str, 0, str.length(), rect);

        return rect.width();
    }


    /** */
    /*****************************************************
     * 功能介绍:将unicode字符串转为汉字 输入参数:源unicode字符串 输出参数:转换后的字符串
     *****************************************************/
    private String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }

    public static String stringToUnicode(char chineseChar) {
        return Integer.toHexString(chineseChar).toUpperCase();
    }

    /**
     * 计算时间
     * @param time
     * @return
     */
    public static String computeTime(long time) {
        String _time = "";
        if (time / 3600 > 1) {

        } else {
            if (time < 60) {
                if (time < 10) {
                    _time = "00:0" + time;
                } else {
                    _time = "00:" + time;
                }
            } else {
                if (time % 60 == 0) {
                    if (time / 60 >= 10) {
                        _time = time / 60 + ":" + "00";
                    } else {
                        _time = "0" + time / 60 + ":" + "00";
                    }
                } else {
                    if (time / 60 >= 10) {
                        if (time % 60 >= 10) {
                            _time = time / 60 + ":" + time % 60;
                        } else {
                            _time = time / 60 + ":0" + time % 60;
                        }
                    } else {
                        if (time % 60 >= 10) {
                            _time = "0" + time / 60 + ":" + time % 60;
                        } else {
                            _time = "0" + time / 60 + ":0" + time % 60;
                        }
                    }
                }
            }
        }
        return _time;
    }

    /**
     * 按照以下规则将YY-MM-DD hh:mm:ss的时间戳字符串转换成合适的时间描述 0～59秒：X秒前； 1分钟～59分钟：X分钟前；
     * 1小时～23小时：X小时前； 1天～6天：X天前； 评论时间到达一周：显示X周前； 评论时间到月：则显示为X月前； 评论时间到年：则显示为X年前；
     *
     * @param timeStamp
     *            YY-MM-DD hh:mm的时间戳字符串
     * @return
     */
    public static String getTimeDescFromTimeStamp(String timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
                Locale.CHINA);
        try {
            Date date = sdf.parse(timeStamp);
            Date curDate = new Date();
            long timeMs = date.getTime();
            long curMs = curDate.getTime();
            long distanceSecond = (curMs - timeMs) / 1000;
            long distanceMinute = distanceSecond / 60;
            long distanceHour = distanceMinute / 60;
            long distanceDay = distanceHour / 24;
            long distanceWeek = distanceDay / 7;
            long distanceMonth = distanceWeek / 4;
            long distanceYear = distanceMonth / 12;
            // Log.i("timeMs-CurMs-disSec", timeMs + "-" + curMs + "-" +
            // distanceSecond);
            if (distanceSecond < 1) {
                return "1秒前";
            } else if (distanceSecond < 60) {
                return distanceSecond + "秒前";
            } else if (distanceMinute < 60) {
                return distanceMinute + "分钟前";
            } else if (distanceHour < 24) {
                return distanceHour + "小时前";
            } else if (distanceDay < 7) {
                return distanceDay + "天前";
            } else if (distanceWeek < 4) {
                return distanceWeek + "周前";
            } else if (distanceMonth < 12) {
                return distanceMonth + "月前";
            } else {
                return distanceYear + "年前";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;

    }

    /**
     * 判断是否全为英文
     *
     * @param word
     * @return
     */
    public static boolean strIsEnglish(String word) {
        // boolean sign = true; // 初始化标志为为'true'
        for (int i = 0; i < word.length(); i++) {
            if (!(word.charAt(i) >= 'A' && word.charAt(i) <= 'Z')
                    && !(word.charAt(i) >= 'a' && word.charAt(i) <= 'z')) {
                return false;
            }
        }
        return true;
    }

}
