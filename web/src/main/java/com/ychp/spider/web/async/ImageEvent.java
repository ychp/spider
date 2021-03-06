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
public class ImageEvent implements Serializable {

    private static final long serialVersionUID = 4497232032610605739L;
    private Long ruleId;
}
