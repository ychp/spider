package com.ychp.user.api.bean.response;

import com.ychp.user.model.DeviceInfo;
import com.ychp.user.model.IpInfo;
import com.ychp.user.model.SeeLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018/8/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeeLogVO implements Serializable {
	private static final long serialVersionUID = 5381667831309303712L;

	private SeeLog seeLog;

	private IpInfo ipInfo;

	private DeviceInfo deviceInfo;
}
