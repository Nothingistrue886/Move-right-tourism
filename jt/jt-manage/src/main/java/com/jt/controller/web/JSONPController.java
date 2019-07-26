package com.jt.controller.web;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemCat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JSONPController {

    @RequestMapping("/web/testJSONP")
    public JSONPObject testJsonp(String callback){
        ItemCat itemCat = new ItemCat();
        itemCat.setId(2000L).setName("jsonp测试！@！！！");
        JSONPObject jsonpObect = new JSONPObject(callback,itemCat);
        return jsonpObect;
    }









    /**
     * jsonp返回值结果,必须经过特殊格式封装.
     * 	 调用者::回调函数获取
     * 	 数据返回:封装数据   callback(JSON串)
     * 	 http://manage.jt.com/web/testJSONP?callback=jQuery111108050409315062621_1563263312902&_=1563263312903
     * @return
     */
//    @RequestMapping("/web/testJSONP")
//    public String testJsonp(String callback){
//        ItemCat itemCat = new ItemCat();
//        itemCat.setId(1000L).setName("jsonp测试!!!");
//        String json = ObjectMapperUtil.toJson(itemCat);
//        return callback+"("+json+")";
//    }
}
