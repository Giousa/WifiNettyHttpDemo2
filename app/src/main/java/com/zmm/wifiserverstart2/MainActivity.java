package com.zmm.wifiserverstart2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zmm.wifiserverstart2.model.DeviceModel;
import com.zmm.wifiserverstart2.netty.NettyManager;
import com.zmm.wifiserverstart2.wifi.WifiManager;

public class MainActivity extends AppCompatActivity implements NettyManager.ReadListener, WifiManager.WifiStartListener {

    private TextView mRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Button start = (Button) findViewById(R.id.btn_start);
        Button second = (Button) findViewById(R.id.btn_second);
        mRead = (TextView) findViewById(R.id.tv_read);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        initServerManager();
//                        startWifiServer();
                    }
                }).start();

            }
        });

    }

    private void initServerManager() {
        wifiStart();
    }

    private void wifiStart() {
        WifiManager wifiManager = new WifiManager();
        wifiManager.setWifiStartListener(this);
        wifiManager.start(this,"HOT-Device");
    }

    @Override
    public void wifiStartListener() {

        System.out.println("Wifi热点已创建");

        startWifiServer();
    }

    private void startWifiServer() {
        NettyManager nettyManager = new NettyManager();
        nettyManager.setReadListener(this);
        nettyManager.start();
    }

    @Override
    public void onReadListener(String msg) {

        final DeviceModel deviceModel = JSON.parseObject(msg, DeviceModel.class);
        System.out.println("收到客户端消息: "+deviceModel.toString());
        System.out.println("主被动速度：Speed = "+deviceModel.getCurSpeed());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                System.out.println("-------------------------");
                mRead.append(deviceModel.toString()+"\r\n");
                if(mRead.getText().length() > 1400){
                    mRead.setText("");
                }
            }
        });

    }

}

