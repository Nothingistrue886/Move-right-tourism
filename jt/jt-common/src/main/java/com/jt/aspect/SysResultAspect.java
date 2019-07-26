package com.jt.aspect;

import com.jt.vo.SysResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class SysResultAspect {

    /**
     * 如果程序报错,则统一返回系统异常信息
     * SysResult.fail()
     */
    @ExceptionHandler({RuntimeException.class}) //如果遇到指定的异常类型执行下列方法
    //@ResponseBody
    public SysResult sysResultFail(Exception e) {
        e.printStackTrace();
        log.error("服务器异常信息:"+e.getMessage());
        return SysResult.fail();
    }
}
