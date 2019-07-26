package com.jt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

public class TestObjectToJson {

    private ObjectMapper objectMapper = new ObjectMapper();;

    /**
     * 将对象转换为字符串
     */
    @Test
    public void toJson(){
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(100L)
                .setItemDesc("我是一个测试用例")
                .setUpdated(new Date())
                .setCreated(new Date());

        try {
            String json = objectMapper.writeValueAsString(itemDesc);
            System.out.println(json);

        /**
         * 将json转化为对象类型
         */

            try {
                ItemDesc itemDesc1 = objectMapper.readValue(json, ItemDesc.class);
                System.out.println(itemDesc1);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
