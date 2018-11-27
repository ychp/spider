/*
 * Copyright (c) 2016. 杭州端点网络科技有限公司.  All rights reserved.
 */

package com.ychp.common.captcha;

import com.github.cage.Cage;
import com.github.cage.IGenerator;
import com.github.cage.image.EffectConfig;
import com.github.cage.image.Painter;
import com.github.cage.token.RandomTokenGenerator;

import javax.annotation.PostConstruct;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
public class CaptchaGenerator {

    private Cage cage;

    private IGenerator<String> tokenGenerator;

    @PostConstruct
    public void init() {
        Painter painter = new Painter(150, 70, null, null, new EffectConfig(true, true, true, true, null), null);
        tokenGenerator = new RandomTokenGenerator(null, 4, 0);
        cage = new Cage(painter, null, null, null, Cage.DEFAULT_COMPRESS_RATIO, tokenGenerator, null);
    }

    public byte[] captcha(String token) {
        return cage.draw(token);
    }

    public String generateCaptchaToken() {
        return tokenGenerator.next();
    }

}
