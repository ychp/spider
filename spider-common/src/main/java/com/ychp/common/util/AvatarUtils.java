package com.ychp.common.util;

/**
 * Desc:
 * Author: <a href="ychp@terminus.io">应程鹏</a>
 * Date: 2017/8/27
 */
public class AvatarUtils {

    public static String generalAvatar(String email) {
        return "https://secure.gravatar.com/avatar/" + Encryption.md5Encode(email);
    }
}
