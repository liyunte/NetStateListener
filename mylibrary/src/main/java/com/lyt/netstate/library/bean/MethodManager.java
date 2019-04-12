package com.lyt.netstate.library.bean;

import com.lyt.netstate.library.type.NetType;

import java.lang.reflect.Method;

/**
 * 网络注解方法封装类
 */
public class MethodManager {
    /**
     * 方法参数
     */
    private Class<?> type;
    /**
     * 注解的参数
     */
    private NetType netType;
    /**
     * 方法
     */
    private Method method;

    public MethodManager(Class<?> type, NetType netType, Method method) {
        this.type = type;
        this.netType = netType;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
