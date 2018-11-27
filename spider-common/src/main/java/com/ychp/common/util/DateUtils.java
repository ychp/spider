package com.ychp.common.util;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Desc:
 * Author: <a href="ychp@terminus.io">应程鹏</a>
 * Date: 2017/8/27
 */
public class DateUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static Date parse2Date(String dateStr) {
        return DATE_FORMATTER.parseDateTime(dateStr).toDate();
    }

    public static Date parse2EndDate(String dateStr) {
        return DATE_FORMATTER.parseDateTime(dateStr).plusDays(1).withTimeAtStartOfDay().minusMillis(1).toDate();
    }

    public static Date parse2Date(String dateStr, String format) {
        return DateTimeFormat.forPattern(format).parseDateTime(dateStr).toDate();
    }

    public static String parse2DateTimeStr(String dateStr) {
        return DATE_FORMATTER.parseDateTime(dateStr).toString("yyyy-MM-dd HH:mm:ss");
    }

    public static String parse2EndDateTimeStr(String dateStr) {
        return DATE_FORMATTER.parseDateTime(dateStr).plusDays(1).withTimeAtStartOfDay().minusMillis(1)
                .toString("yyyy-MM-dd HH:mm:ss");
    }
}
