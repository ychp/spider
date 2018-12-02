package com.ychp.spider.utils;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


/**
 * @author yingchengpeng
 * @date 2018-12-02
 */
@Slf4j
public class HttpDownloadUtil {

    private static final Header[] HEADERS = new Header[]{new BasicHeader("accept", "*/*"),
            new BasicHeader("connection", "keep-alive"),
            new BasicHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.75 Safari/537.36 QQBrowser/4.1.4132.400")
    };

    public static boolean download(String urlString, String path, String fileName){
        InputStream is = null;
        OutputStream os = null;

        HttpGet httpGet = new HttpGet(urlString);
        try {
            //采用绕过验证的方式处理https请求
            SSLContext sslcontext = createIgnoreVerifySSL();

            // 设置协议http和https对应的处理socket链接工厂的对象
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslcontext))
                    .build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

            CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
            httpGet.setHeaders(HEADERS);
            HttpResponse response = client.execute(httpGet);
            log.error("http error {}", response);

            // 输入流
            log.info("download file[name = {}, url = {}], size = {}",
                    fileName, urlString, response.getEntity().getContentLength());
            is = response.getEntity().getContent();

            // 1K的数据缓冲
            byte[] bs = new byte[1024 * 1024 * 8];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            path = new String(path.getBytes("ISO-8859-1"),"UTF-8");
            File sf = new File(path);
            if (!sf.exists()) {
                sf.mkdirs();
            }
            os = new FileOutputStream(sf.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
                os.flush();
            }
            return true;
        } catch (Exception e) {
            log.error("download(url = {}) file fail, case {}", urlString, Throwables.getStackTraceAsString(e));
            return false;
        } finally {
            // 完毕，关闭所有链接
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 绕过验证
     */
    private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

}
