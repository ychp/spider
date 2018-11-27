package com.ychp.handlebars;

import com.github.jknack.handlebars.Handlebars;
import com.ychp.handlebars.helps.AssertHelper;
import com.ychp.handlebars.helps.EqualsHelper;
import com.ychp.handlebars.helps.FormatDateHelper;
import com.ychp.handlebars.helps.FormatParamHelper;

/**
 * @author yingchengpeng
 * @date 2018/8/8
 */
public class HandlebarsFactory {

	public static Handlebars getInstance(){
		Handlebars handlebars = new Handlebars();
		handlebars.registerHelper("equals", new EqualsHelper());
		handlebars.registerHelper("formatDate", new FormatDateHelper());
		handlebars.registerHelper("formatParam", new FormatParamHelper());
		handlebars.registerHelper("assert", new AssertHelper());
		return handlebars;
	}
}
