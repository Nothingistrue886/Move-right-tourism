package com.jt;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class TestHttpClient {
    /**
     * 编码思路:
     * 	 * 	1.创建工具API对象
     * 	 * 	2.定义远程url地址
     * 	 *  3.定义请求类型对象
     * 	 *  4.发起http请求,获取响应结果.
     * 	 *  5.从响应对象中获取数据.
     */
    @Test
    public void testHttpClient() throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://doc.tedu.cn/eclipse/windows.html");
        CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == 200) {
            // 获取相应数据 json
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } else {
            System.out.println("请求失败请稍后重试");
        }
    }
}
