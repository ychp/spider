package com.ychp.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@Data
public class SkyUser implements Serializable {

    private static final long serialVersionUID = -6041819789771031794L;
    private Long id;
    private String name;
    private String nickName;
    private String mobile;
    private String email;
    private String ip;
    private String avatar;
    private Integer status;

}
