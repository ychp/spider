package com.ychp.common.util;

import org.junit.Test;

/**
 * Desc:
 * Author: <a href="ychp@terminus.io">应程鹏</a>
 * Date: 2017/8/27
 */
public class EncryptionTest {

    @Test
    public void encrypt() {
        String salt = "grqx5iCM2Ma8KT9x1hja6acW";
        System.out.println("key:" + salt);
        String password = "123456";
        System.out.println("password:" + Encryption.md5Encode(password, salt));
        password = Encryption.encrypt3DES(password, salt);
        System.out.println("password:" + password);
        password = Encryption.decrypt3DES(password, salt);
        System.out.println("origin_password:" + password);
    }

}
