package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

// 系统返回值VO对象
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {
    private Integer status; // 200表示成功 201表示失败
    private String msg;     // 服务器提示信息
    private Object data;    // 服务器返回业务数据

    public static SysResult success() {
        return new SysResult(200, "调用成功!!", null);
    }
    public static SysResult success(Object data) {
        return new SysResult(200, "调用成功!!", data);
    }
    public static SysResult success(String msg, Object data) {
        return new SysResult(200, msg, data);
    }
    public static SysResult fail(){
        return new SysResult(201, "调用失败!!",null);
    }
}
