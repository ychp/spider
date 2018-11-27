package com.ychp.handlebars.helps;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

/**
 * @author yingchengpeng
 * @date 2018/8/8
 */
public class FormatParamHelper implements Helper<String> {

	@Override
	public CharSequence apply(String content, Options options) throws IOException {
		if (content == null){
			return null;
		}
		return "{" + content + "}";
	}
}
