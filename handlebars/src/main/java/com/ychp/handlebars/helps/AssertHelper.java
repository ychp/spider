package com.ychp.handlebars.helps;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

/**
 * @author yingchengpeng
 * @date 2018/8/8
 */
public class AssertHelper implements Helper<Boolean> {

	@Override
	public CharSequence apply(Boolean param, Options options) throws IOException {
		CharSequence result;
		if ((param != null) && param) {
			result = options.fn(param);
		} else {
			result = options.inverse(param);
		}
		return result;
	}
}
