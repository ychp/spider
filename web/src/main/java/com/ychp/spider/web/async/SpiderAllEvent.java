package com.ychp.spider.web.async;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author yingchengpeng
 * @date 2018/12/02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpiderAllEvent implements Serializable {

    private static final long serialVersionUID = -8729971137837086669L;
    private List<SpiderEvent> rules;

}

