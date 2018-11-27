package com.ychp.handlebars.helps;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

/**
 * @author yingchengpeng
 * @date 2018/8/8
 */
public class EqualsHelper implements Helper<String> {

	@Override
	public CharSequence apply(String content, Options options) throws IOException {
		CharSequence result;
		String left = options.param(0).toString();
		if ((content != null) && (left != null)) {
			if (content.equals(left)) {
				result = options.fn(content);
			} else {
				result = options.inverse(content);
			}
			return result;
		} else {
			return null;
		}
	}
}
