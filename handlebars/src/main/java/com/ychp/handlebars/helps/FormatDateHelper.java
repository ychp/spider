package com.ychp.handlebars.helps;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.Date;

/**
 * @author yingchengpeng
 * @date 2018/8/8
 */
public class FormatDateHelper implements Helper<Date> {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy/MM/dd");

	@Override
	public CharSequence apply(Date content, Options options) throws IOException {
		if (content == null){
			return null;
		}
		return new DateTime(content).toString(DATE_TIME_FORMATTER);
	}
}
