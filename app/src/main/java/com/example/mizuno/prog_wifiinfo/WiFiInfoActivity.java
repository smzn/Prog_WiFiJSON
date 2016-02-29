package com.example.mizuno.prog_wifiinfo;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class WiFiInfoActivity extends AppCompatActivity {

    ListView listView;
    private AsyncHttp asynchttp;
    WifiInfo info;
    int ip_addr_i;
    String ip_addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_info);

        Button button = (Button)findViewById(R.id.button);
        Button button2 = (Button)findViewById(R.id.button2);
        Button button3 = (Button)findViewById(R.id.button3);
        listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //WiFiの情報を取得する
                //ACCESS_WIFI_STATEのパーミッションをマニフェストに追加する
                WifiManager manager = (WifiManager)getSystemService(WIFI_SERVICE);
                info = manager.getConnectionInfo();

                ip_addr_i = info.getIpAddress();
                ip_addr = ((ip_addr_i >> 0) & 0xFF) + "." + ((ip_addr_i >> 8) & 0xFF) + "." + ((ip_addr_i >> 16) & 0xFF) + "." + ((ip_addr_i >> 24) & 0xFF);

                String[] members = {
                        "SSID: " + info.getSSID(),
                        "IPAddress: " + ip_addr,
                        "MACAddress: " + String.valueOf(info.getMacAddress()),
                        "RSSI: " + String.valueOf(info.getRssi())
                };
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(WiFiInfoActivity.this, android.R.layout.simple_list_item_1, members);
                listView.setAdapter(adapter);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip_addr_i = info.getIpAddress();
                ip_addr = ((ip_addr_i >> 0) & 0xFF) + "." + ((ip_addr_i >> 8) & 0xFF) + "." + ((ip_addr_i >> 16) & 0xFF) + "." + ((ip_addr_i >> 24) & 0xFF);
                asynchttp = new AsyncHttp(android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID));
                asynchttp.execute(info.getSSID(), ip_addr, String.valueOf(info.getMacAddress()), String.valueOf(info.getRssi()));

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WiFiInfoActivity.this, WiFiRecordActivity.class);
                startActivity(intent);
            }
        });
    }
}
