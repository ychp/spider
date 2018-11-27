package com.ychp.ip.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.ychp.ip.enums.IPAPIType;
import com.ychp.ip.model.BaiduIpAddress;
import com.ychp.ip.model.IpAddress;
import com.ychp.ip.model.SinaIpAddress;
import com.ychp.ip.model.TaobaoIpAddress;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author yingchengpeng
 * @date 2018-08-09
 */
@Slf4j
@NoArgsConstructor
public class IPServer {

    public IPServer(String baiduApiKey, ObjectMapper objectMapper) {
        this.baiduApiKey = baiduApiKey;
        this.objectMapper = objectMapper;
    }
    private static String baiduApiKey;

    private static ObjectMapper objectMapper;

    private static final String BAIDU_IP_API_URL = "http://apis.baidu.com/apistore/iplookupservice/iplookup?ip=";

    private static final String TAOBAO_IP_API_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    private static final String SINA_IP_API_URL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";

    private static final String UNKNOWN_IP = "unknown";

    private static final String LOCAL_IP = "127.0.0.1";

    public String getIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return getFinalIp(ip);
    }

    public String getFinalIp(String originIp) {
        if(!originIp.contains(",")) {
            return originIp;
        }
        String[] ips = originIp.split(",");
        for(String ip : ips) {
            if(!LOCAL_IP.equals(ip.trim())) {
                return ip.trim();
            }
        }

        return LOCAL_IP;
    }

    public String getHost(HttpServletRequest request){
        return request.getRemoteHost();
    }

    /**
     * 获取ip归属地
     */
    public IpAddress getIpAddress(String ip, Integer type){
        IpAddress result;
        try {
            if(type == null){
                    result = taobaoIpAddress(ip);
            } else if(Objects.equal(IPAPIType.BAIDU.value(), type)){
                result = baiduIpAddress(ip, baiduApiKey);
            } else if(Objects.equal(IPAPIType.TAOBAO.value(), type)){
                result = taobaoIpAddress(ip);
            } else if(Objects.equal(IPAPIType.SINA.value(), type)){
                result = sinaIpAddress(ip);
            } else{
                result = taobaoIpAddress(ip);
            }
        } catch (IOException e) {
            log.error("fail to get ip address, case {}", Throwables.getStackTraceAsString(e));
            return null;
        }

        if(result == null) {
            result = new IpAddress();
            result.setProvince("未知");
            result.setCity("未知");
            result.setCountry("未知");
        }

        if(StringUtils.isEmpty(result.getProvince())){
            result.setProvince(result.getCountry());
        }
        if(StringUtils.isEmpty(result.getCity())){
            result.setCity(result.getCountry());
        }
        return result;
    }


    /**
     * 获取ip归属地(百度)
     */
    private IpAddress baiduIpAddress(String ip, String apiKey) throws IOException {
        IpAddress result = new IpAddress();
        String str = get(BAIDU_IP_API_URL + ip, true, apiKey);
        if(StringUtils.isEmpty(str)) {
            log.warn("fail to get ip address by ip = {}", ip);
            return null;
        }
        BaiduIpAddress ipAddress = objectMapper.readValue(str, BaiduIpAddress.class);
        if(ipAddress != null  && ipAddress.getErrNum() == 0) {
            result.setSuccess(true);
            if(ipAddress.getRetData() != null) {
                result.setProvince(ipAddress.getRetData().getProvince());
                result.setCity(ipAddress.getRetData().getCity());
                result.setCountry(ipAddress.getRetData().getCountry());
                result.setIsp(ipAddress.getRetData().getCarrier());
            }else{
                log.info("ipAddress : {}", ipAddress);
            }
        }

        return result;
    }

    /**
     * 获取ip归属地(淘宝)
     */
    private IpAddress taobaoIpAddress(String ip) throws IOException {
        IpAddress result = new IpAddress();
        String str = get(TAOBAO_IP_API_URL + ip, false, null);
        if(StringUtils.isEmpty(str)) {
            log.warn("fail to get ip address by ip = {}", ip);
            return null;
        }
        TaobaoIpAddress ipAddress = objectMapper.readValue(str, TaobaoIpAddress.class);
        if(ipAddress != null  && ipAddress.getCode()==0) {
            result.setSuccess(true);
            if(ipAddress.getData() != null){
                result.setProvince(ipAddress.getData().getRegion());
                result.setCity(ipAddress.getData().getCity());
                result.setCountry(ipAddress.getData().getCountry());
                result.setIsp(ipAddress.getData().getIsp());
                log.info("ipAddress : {}", ipAddress);
            } else {
                log.info("ipAddress : {}", ipAddress);
            }
        }
        return result;
    }

    /**
     * 获取ip归属地(新浪)
     */
    private IpAddress sinaIpAddress(String ip) throws IOException {
        IpAddress result = new IpAddress();
        String str = get(SINA_IP_API_URL + ip, false, null);
        if(StringUtils.isEmpty(str)) {
            log.warn("fail to get ip address by ip = {}", ip);
            return null;
        }
        SinaIpAddress ipAddress = objectMapper.readValue(str, SinaIpAddress.class);
        if(ipAddress != null  && ipAddress.getRet()==1) {
            result.setSuccess(true);
            result.setProvince(ipAddress.getProvince());
            result.setCity(ipAddress.getCity());
            result.setCountry(ipAddress.getCountry());
            result.setIsp(ipAddress.getIsp());
        }
        return result;
    }

    private String get(String requestUrl, Boolean isCheck, String apiKey){
        BufferedReader reader;
        String str = null;
        StringBuilder sbf = new StringBuilder();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            if(isCheck) {
                connection.setRequestProperty("apikey", apiKey);
            }
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            str = sbf.toString();
        } catch (Exception e) {
            log.error("fail to request by {}", requestUrl, Throwables.getStackTraceAsString(e));
        }
        return str;
    }

}
