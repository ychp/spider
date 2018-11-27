package com.ychp.common.util;

import com.ychp.common.exception.InvalidException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * @author yingchengpeng
 * @date 2018/9/11
 */
public class ParameterChecker {

    public static void notNull(Object param, String paramName, String error) {
        if(param == null) {
            throw new InvalidException(error, paramName, param);
        }
        if(param instanceof String && StringUtils.isEmpty((String) param)) {
            throw new InvalidException(error, paramName, param);
        }

        if(param instanceof Collection && ((Collection) param).isEmpty()) {
            throw new InvalidException(error, paramName, param);
        }
    }

}
