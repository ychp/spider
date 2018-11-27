package com.ychp.user.api.bean.request;

import com.ychp.user.model.DeviceInfo;
import com.ychp.user.model.IpInfo;
import com.ychp.user.model.SeeLog;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yingchengpeng
 * @date 2018/8/11
 */
@Data
public class SeeLogCreateRequest implements Serializable {
	private static final long serialVersionUID = 5381667831309303712L;

	private SeeLog seeLog;

	private IpInfo ipInfo;

	private DeviceInfo deviceInfo;
}
