package com.example.mizuno.prog_wifiinfo;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mizuno on 2016/01/14.
 */
public class AsyncTaskGetJson extends AsyncTask<Void, Void, String> {

    private String API_URL;
    private WiFiRecordActivity activity;
    private String id;

    public AsyncTaskGetJson(WiFiRecordActivity activity, String id) {
        this.activity = (WiFiRecordActivity) activity;
        this.id = id;
        API_URL = "http://j11000.sangi01.net/cakephp/wifis/api/" + this.id;
    }

    @Override
    protected String doInBackground(Void... voids) {

        String result = new String();
        ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(API_URL);
            httpPost.setEntity(new UrlEncodedFormEntity(postData, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                String data = EntityUtils.toString(responseEntity);

                JSONObject rootObject = new JSONObject(data);

                JSONArray userArray = rootObject.getJSONArray("Wifi");
                Log.d("json1_data", userArray.toString());

                for (int n = 0; n < userArray.length(); n++) {
                    // User data
                    JSONObject userObject = userArray.getJSONObject(n);
                    String id = userObject.getString("id");
                    String device_id = userObject.getString("device_id");
                    String ssid = userObject.getString("ssid");
                    String ipaddress = userObject.getString("ipaddress");
                    String macaddress = userObject.getString("macaddress");
                    String rssi = userObject.getString("rssi");
                    //result += "ID"+ userId + "\r\n" + "User: "+userName+" \r\n" +"緯度: " +userLatitude+"\r\n" +"経度: " +userLongitude +"\r\n";
                    result += id + "," + device_id + "," + ssid + "," + ipaddress + "," + macaddress + "," + rssi + ",";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        //double[] geo = new double[20];
        String str[] = s.split(",", 0);
        Log.d("STR_LENGTH", String.valueOf(str.length));
        //double wifidata[] = new double[str.length];
//        for(int i = 0; i< wifidata.length; i++) wifidata[i] = Double.parseDouble(str[i]);
        activity.setListView(str);

    }
}
