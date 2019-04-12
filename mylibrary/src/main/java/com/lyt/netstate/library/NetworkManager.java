package com.lyt.netstate.library;

import android.app.Application;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import com.lyt.netstate.library.utils.Constants;
import com.lyt.netstate.library.utils.Nulls;

public class NetworkManager {

    private static volatile NetworkManager instance;
    private Application application;
    private NetStateReceiver receiver;

    private NetworkManager() {
        receiver = new NetStateReceiver();
    }

    public static NetworkManager getDefault() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    public void init(@NonNull Application application) {
        Nulls.requireNonNull(application,"NetworkManager.init(application) 形参不能为null");
        this.application = application;
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        application.registerReceiver(receiver, filter);
    }

    public void registerObserver(@NonNull Object register) {
        Nulls.requireNonNull(register,"NetworkManager.registerObserver(object) 形参不能为null");
        receiver.registerObserver(register);
    }


    public void unRegisterObserver(@NonNull Object register) {
        Nulls.requireNonNull(register,"NetworkManager.unRegisterObserver(register) 形参不能为null");
        receiver.unRegisterObserver(register);
    }

    public void unReigsterAllObserver() {
        receiver.unReigsterAllObserver();
    }

    public Application getApplication() {
        if (application == null) {
            throw new RuntimeException("NetworkManager must init() in your Application.onCreate()");
        }
        return application;
    }
}
