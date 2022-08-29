package com.deepblue.libraries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.deepblue.appotalib.AppUpgradeStatus;

public class OtaActivity extends AppCompatActivity {
    private Button setMac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ota);
        AppUpgradeStatus.init(this);
        setMac = findViewById(R.id.setMac);
    }

    public void CBClick(View v){
        Log.d("ota","set mac address");
        AppUpgradeStatus.setDfuMacAddress("28:11:A5:C9:2F:A6");
    }

    public void CBOtaClick(View v){
        Log.d("ota","set can ota ");
        AppUpgradeStatus.setAppUpgradeStatus(true);
    }

    public void CBOtaStopClick(View v){
        Log.d("ota","set can't ota");
        AppUpgradeStatus.setAppUpgradeStatus(false);
    }
}
