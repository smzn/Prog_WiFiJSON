package com.example.mizuno.prog_wifiinfo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WiFiRecordActivity extends ActionBarActivity {

    ListView listView2;
    private AsyncTaskGetJson getjson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_record);

        listView2 = (ListView)findViewById(R.id.listView2);
        getjson = new AsyncTaskGetJson(this,"4fa0c24105b89691");
        getjson.execute();

    }

    public void setListView(String str[]){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(WiFiRecordActivity.this, android.R.layout.simple_list_item_1);
        for(int i = 0; i< str.length; i+=6 ){
            adapter.add("ID: "+ str[i]);
            adapter.add("device_id: " + str[i+1]);
            adapter.add("SSID: " + str[i+2]);
            adapter.add("IPAddress: " + str[i+3]);
            adapter.add("MACAddress: " + str[i+4]);
            adapter.add("RSSI: " + str[i+5]);
        }
        listView2.setAdapter(adapter);
    }
}
