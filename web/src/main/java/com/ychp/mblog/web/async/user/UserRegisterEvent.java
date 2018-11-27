package com.ychp.spider.web.async.user;

import com.ychp.common.model.SkyUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018/8/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterEvent implements Serializable {
	private static final long serialVersionUID = 2881264148965560004L;

	private SkyUser skyUser;
}
