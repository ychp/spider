package com.ychp.spider.web.controller.bean;

import com.ychp.ip.component.IPServer;
import com.ychp.ip.enums.IPAPIType;
import com.ychp.ip.model.IpAddress;
import com.ychp.request.model.UserAgent;
import com.ychp.request.util.RequestUtils;
import com.ychp.user.model.DeviceInfo;
import com.ychp.user.model.IpInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LogConverter {

    @Autowired
    private IPServer ipServer;

    public IpInfo getIpInfo(String ip) {
        IpInfo ipInfo = new IpInfo();
        IpAddress ipAddress = ipServer.getIpAddress(ip, IPAPIType.TAOBAO.value());
        ipInfo.setIp(ip);
        ipInfo.setCountry(ipAddress.getCountry());
        ipInfo.setProvince(ipAddress.getProvince());
        ipInfo.setCity(ipAddress.getCity());
        return ipInfo;
    }

    public DeviceInfo getDeviceInfo(HttpServletRequest request) {
        DeviceInfo deviceInfo = new DeviceInfo();
        UserAgent userAgent = RequestUtils.getUaInfo(request);
        deviceInfo.setOs(userAgent.getSystem());
        deviceInfo.setBrowser(userAgent.getBrowser());
        deviceInfo.setBrowserVersion(userAgent.getBrowserVersion());
        deviceInfo.setDevice(userAgent.getDevice());
        return deviceInfo;
    }
}
