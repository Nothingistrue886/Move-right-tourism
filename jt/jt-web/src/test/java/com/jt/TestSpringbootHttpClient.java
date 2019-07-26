package com.jt;

import com.jt.util.HttpClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpringbootHttpClient {
    @Autowired
    private HttpClientService httpClientService;
    @Test
    public void testHttpClientService(){
        String url = "http://doc.tedu.cn/tstore_v1/index.html";
        String result = httpClientService.doGet(url);
        System.out.println(result);
    }
}
