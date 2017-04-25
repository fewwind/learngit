package com.example.fewwind.keymap;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.fewwind.keymap.test.PhoneWindow;
import com.example.fewwind.keymap.test.Test1;

public class CallBackActivity extends AppCompatActivity implements  Test1.CallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_back);

        PhoneWindow phoneWindow = new PhoneWindow();
        phoneWindow.setCallBack(this);

        WifiManager wifiManager= (WifiManager) getSystemService(Context.WIFI_SERVICE);

    }


    @Override public void dispatchKeyEvent() {

    }
}
