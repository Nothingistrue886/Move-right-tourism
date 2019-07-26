package com.jt.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

@Service
public class HttpClientService {
    @Autowired
    private RequestConfig requestConfig;
    @Autowired
    private CloseableHttpClient closeableHttpClient;
    /**
     * url://www.jt.com?id=1&name=tomcat
     *  目的:发起请求获取服务器数据
     *  参数说明:
     * 	1.url地址
     * 	2.用户提交的参数使用Map封装
     * 	3.指定编码格式
     *
     * 	步骤:
     * 	1.校验字符集. 如果字符集为null 设定默认值
     * 	2.校验params是否为null
     * 		null:表示用户get请求无需传参.
     * 		!null:需要传参,  get请求规则 url?key=value&key2=value2...
     * 	3.发起http的get请求获取返回值结果
     */

    public String doGet(String url, Map<String,String> params, String charset){
        // 1.校验字符集
        if(StringUtils.isEmpty(charset)){
            charset = "UTF-8";

        }
        // 2.校验参数是否为null
        if(params != null){
            url += "?";
            //2.1遍历map集合
            for (Map.Entry<String,String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                url = url + key + "=" + value + "&";
            }
            url = url.substring(0, url.length() - 1);
        }
        // 3.发起httpGET请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);   //定义请求超时时间
        String result = null;
        try {
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200){
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    //重载方法.对方法进行扩充方便使用者调用
    public String doGet(String url){
        return doGet(url, null, null);
    }
    public String doGet(String url, Map<String,String> params){
        return doGet(url, params, null);
    }

}
