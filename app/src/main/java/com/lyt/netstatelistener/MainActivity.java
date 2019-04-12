package com.lyt.netstatelistener;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lyt.netstate.library.NetworkManager;
import com.lyt.netstate.library.annotation.Network;
import com.lyt.netstate.library.type.NetType;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkManager.getDefault().init(getApplication());
        NetworkManager.getDefault().registerObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getDefault().unRegisterObserver(this);
    }

    @Network(netType = NetType.AUTO)
    public void netCheck(NetType netType){
        switch (netType){
            case AUTO:
                Log.e(LOG_TAG,"AUTO");
                break;
            case NONE:
                Log.e(LOG_TAG,"NONE");
                break;
            case CMWAP:
                Log.e(LOG_TAG,"CMWAP");
                break;
            case CMNET:
                Log.e(LOG_TAG,"CMNET");
                break;
            case WIFI:
                Log.e(LOG_TAG,"WIFI");
                break;
        }

    }
    @Network(netType = NetType.NONE)
    public void netCheck2(NetType netType){
        switch (netType){
            case AUTO:
                Log.e(LOG_TAG,"AUTO");
                break;
            case NONE:
                Log.e(LOG_TAG,"NONE");
                break;
            case CMWAP:
                Log.e(LOG_TAG,"CMWAP");
                break;
            case CMNET:
                Log.e(LOG_TAG,"CMNET");
                break;
            case WIFI:
                Log.e(LOG_TAG,"WIFI");
                break;
        }

    }
}
