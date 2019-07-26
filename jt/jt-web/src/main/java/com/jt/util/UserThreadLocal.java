package com.jt.util;

import com.jt.pojo.User;

public class UserThreadLocal {
    public static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void setThreadLocal(User user){
        threadLocal.set(user);
    }

    public static User getThreadLocal(){
        return threadLocal.get();
    }
    //防止内存泄漏remove()
    public static void removeThreadLocal(){
        threadLocal.remove();
    }
}
