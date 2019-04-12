package com.lyt.netstate.library.type;

import android.support.annotation.Keep;

@Keep
public enum NetType {
    //只要有网络，不关心是wifi/gprs
    AUTO,
    WIFI,
    CMNET,
    CMWAP,
    //无网络
    NONE
}
