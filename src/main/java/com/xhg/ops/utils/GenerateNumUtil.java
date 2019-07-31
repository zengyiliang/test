package com.xhg.ops.utils;

import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * 自动生成8位密码
 */
public class GenerateNumUtil {

    public static String getRandomCharAndNumr(Integer length) {
        String str = "";

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) { // 字符串
                // int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
                str += (char) (65 + random.nextInt(26));// 取得大写字母
            } else { // 数字
                str += String.valueOf(random.nextInt(10));
            }
        }
        return str;
    }

    /**
     * 验证随机字母数字组合是否纯数字与纯字母
     *
     * @param str
     * @return true 是 ， false 否
     */
    public static boolean isRandomUsable(String str) {
        // String regExp =
        // "^[A-Za-z]+(([0-9]+[A-Za-z0-9]+)|([A-Za-z0-9]+[0-9]+))|[0-9]+(([A-Za-z]+[A-Za-z0-9]+)|([A-Za-z0-9]+[A-Za-z]+))$";
        String regExp = "^([0-9]+)|([A-Za-z]+)$";
        Pattern pat = Pattern.compile(regExp);
        Matcher mat = pat.matcher(str);
        return mat.matches();

    }
}
