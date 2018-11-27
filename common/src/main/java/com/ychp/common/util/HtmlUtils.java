package com.ychp.common.util;

/**
 * @author yingchengpeng
 * @date 2018-08-14
 */
public class HtmlUtils {

    public static String splitAndFilterString(String input, int length) {
        if (input == null || "".equals(input.trim())) {
            return "";
        }
        // 去掉所有html元素,
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                "<[^>]*>", "");
        str = str.replaceAll("[(/>)<]", "");
        int len = str.length();
        if (len <= length) {
            return str;
        } else {
            str = str.substring(0, length);
            str += "......";
        }
        return str;
    }

}
