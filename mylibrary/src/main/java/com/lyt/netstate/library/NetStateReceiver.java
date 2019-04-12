package com.lyt.netstate.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lyt.netstate.library.annotation.Network;
import com.lyt.netstate.library.bean.MethodManager;
import com.lyt.netstate.library.type.NetType;
import com.lyt.netstate.library.utils.Constants;
import com.lyt.netstate.library.utils.NetworkUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NetStateReceiver extends BroadcastReceiver {
    private NetType netType;//网络类型
    private Map<Object, List<MethodManager>> networkList;

    public NetStateReceiver() {
        this.netType = NetType.NONE;
        networkList = new HashMap<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)) {
            Log.e(Constants.LOG_TAG, "网络发生改变了");
            netType = NetworkUtils.getNetType();
            if (NetworkUtils.isNetworkAvailable()) {
                Log.e(Constants.LOG_TAG, "网络连接成功");
            } else {
                Log.e(Constants.LOG_TAG, "网络发生改变了");
            }
            post(netType);
        }
    }

    /**
     * 网络事件分发
     *
     * @param netType
     */
    private void post(NetType netType) {
        Set<Object> set = networkList.keySet();
        for (Object getter : set) {
            List<MethodManager> methodList = networkList.get(getter);
            if (methodList != null) {
                for (MethodManager method : methodList) {
                    if (method.getType().isAssignableFrom(netType.getClass())) {
                        switch (method.getNetType()) {
                            case AUTO:
                                invoke(method, getter, netType);
                                break;
                            case WIFI:
                                if (netType == NetType.WIFI || netType == NetType.NONE) {
                                    invoke(method, getter, netType);
                                }
                                break;
                            case CMNET:
                                if (netType == NetType.CMNET || netType == NetType.NONE) {
                                    invoke(method, getter, netType);
                                }
                                break;
                            case CMWAP:
                                if (netType == NetType.CMWAP || netType == NetType.NONE) {
                                    invoke(method, getter, netType);
                                }
                                break;
                            case NONE:
                                if (netType == NetType.NONE) {
                                    invoke(method, getter, netType);
                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 反射执行方法
     *
     * @param method
     * @param getter
     * @param netType
     */
    private void invoke(MethodManager method, Object getter, NetType netType) {
        try {
            method.getMethod().invoke(getter, netType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反注册所有监听并反注册广播
     */
    public void unReigsterAllObserver() {
        if (!networkList.isEmpty()) {
            networkList.clear();
        }
        NetworkManager.getDefault().getApplication().unregisterReceiver(this);
        networkList = null;
    }

    /**
     * 反注册监听
     *
     * @param register
     */
    public void unRegisterObserver(Object register) {
        if (!networkList.isEmpty()) {
            networkList.remove(register);
        }
    }

    /**
     * 添加监听
     *
     * @param register
     */
    public void registerObserver(Object register) {
        List<MethodManager> methodList = networkList.get(register);
        if (methodList == null) {
            methodList = findAnnotationMethod(register);
            networkList.put(register, methodList);
        }
    }

    /**
     * 查找register中所有使用@Network注解的方法，封装成MethodManager并保存到集合中
     *
     * @param register
     * @return
     */
    private List<MethodManager> findAnnotationMethod(Object register) {
        List<MethodManager> methodList = new ArrayList<>();
        Class<?> clazz = register.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Network network = method.getAnnotation(Network.class);
            if (network == null) {
                continue;
            }
            //方法注解的校验
            Type returnType = method.getGenericReturnType();
            if (!"void".equalsIgnoreCase(returnType.toString())) {
                throw new RuntimeException("方法返回不是void");
            }

            Class<?>[] parmeterTypes = method.getParameterTypes();
            //方法返回类型检查
            if (parmeterTypes.length != 1) {
                throw new RuntimeException(method.getName() + "方法有且只有一个");
            }
            MethodManager methodManager = new MethodManager(parmeterTypes[0], network.netType(), method);
            methodList.add(methodManager);

        }
        return methodList;
    }
}
