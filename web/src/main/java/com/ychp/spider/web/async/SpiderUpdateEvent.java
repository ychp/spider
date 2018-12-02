package com.ychp.spider.web.async;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018/12/02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpiderUpdateEvent implements Serializable {

    private static final long serialVersionUID = 3731240615175378524L;
    private Long ruleId;

    private Long parserId;
}
