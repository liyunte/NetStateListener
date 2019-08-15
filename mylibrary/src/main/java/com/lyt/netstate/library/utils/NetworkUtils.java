package com.lyt.netstate.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.lyt.netstate.library.NetworkManager;
import com.lyt.netstate.library.type.NetType;

public class NetworkUtils {
    /**
     * 网络是否可用
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager) NetworkManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) return false;
        NetworkInfo[] infos = connMgr.getAllNetworkInfo();
        if (infos != null) {
            for (NetworkInfo info : infos) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前网络类型
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static NetType getNetType() {
        ConnectivityManager connMgr = (ConnectivityManager) NetworkManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) return NetType.NONE;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) return NetType.NONE;
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extrainfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extrainfo)){
                if (extrainfo.toLowerCase().equals("cmnet")){
                    return NetType.CMNET;
                }
            }else {
                return NetType.CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }

        return NetType.NONE;
    }

}
