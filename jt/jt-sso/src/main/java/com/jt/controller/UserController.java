package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.service.UserService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * 经过跨域请求 返回系统数据 data:true(已存在)/false(不存在)
	 * http://sso.jt.com/user/check/asfasfasfasfasdafs/1?r=0.18628019862017364&callback=jsonp1563327478623&_=1563327481880
	 */
	@RequestMapping("check/{param}/{type}")
	public JSONPObject findCheckUser(String callback, @PathVariable String param, @PathVariable Integer type){

		JSONPObject jsonpObject = null;
		try {
			//查询数据库,检查数据是否存在
			boolean flag = userService.findUserCheck(param, type);
			jsonpObject = new JSONPObject(callback, SysResult.success(flag));
		} catch (Exception e) {
			e.printStackTrace();
			jsonpObject = new JSONPObject(callback, SysResult.fail());
		}
		return jsonpObject;
	}


	@RequestMapping("query/{token}")
	public JSONPObject findUserByToken(String callback, @PathVariable  String token){
		//1.根据秘钥查询用户信息
		String userJson = jedisCluster.get(token);
        JSONPObject jsonpObject = null;
		if(StringUtils.isEmpty(userJson)){
            jsonpObject = new JSONPObject(callback, SysResult.fail());
        }else {
            //2.表示用户数据获取成功
		    jsonpObject = new JSONPObject(callback, SysResult.success(userJson));
        }
        return jsonpObject;
	}
	
}
