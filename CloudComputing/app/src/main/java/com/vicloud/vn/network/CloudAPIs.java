package com.vicloud.vn.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.vicloud.vn.LoginActivity;
import com.vicloud.vn.models.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by huunc on 7/22/16.
 */
public class CloudAPIs {
    private static final String DOMAIN = "http://222.255.46.173/pub-api/v1.0/get-data-monitor";
    private static final String DOMAIN_GET_METRIC = "http://222.255.46.173/pub-api/v1.0/get-data-metric";
    private static final String URL_lOGIN = "https://vicloud.vn/api/v1/merchant/login";
    private static final String URL_GET_VPS = "https://vicloud.vn/api/v1/merchant/getVPS";
    private static final String URL_REGISTER_FCM = "http://103.237.98.114:8090/register";
    public static boolean isConnectingToInternet(Context mContext){
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++){
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
        }
        return false;
    }
    public static int registerFCM(String token) {
        int httpResponseCode = 401;
        try {
            URL url = new URL(URL_REGISTER_FCM);
            URLConnection urlConn = url.openConnection();
            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("Accept", "application/json");
            httpConn.setRequestMethod("POST");
            httpConn.connect();
            JSONObject dataJson = new JSONObject();
            dataJson.put("token", token);
            OutputStreamWriter out = new   OutputStreamWriter(httpConn.getOutputStream());
            out.write(dataJson.toString());
            out.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return httpResponseCode;
    }

    public static Response getToken(String[] input) {
        int httpResponseCode = 401;
        Response response = new Response();
        try {
            URL url = new URL(URL_lOGIN);
            URLConnection urlConn = url.openConnection();
            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("Accept", "application/json");
            httpConn.setRequestMethod("POST");
            httpConn.connect();
            JSONObject dataJson = new JSONObject();
            dataJson.put("email", input[0]);
            dataJson.put("password", input[1]);
            OutputStreamWriter out = new   OutputStreamWriter(httpConn.getOutputStream());
            out.write(dataJson.toString());
            out.close();
            String data = null;
            StringBuilder sb = new StringBuilder();
            httpResponseCode = httpConn.getResponseCode();
            response.setHttpCode(httpResponseCode);
            if (httpResponseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null)
                    sb.append(line + "\n");
                br.close();
                data = sb.toString();
                response.setData(data);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static Response getListVPS(Context mContext) throws SocketTimeoutException {
        Response response = new Response();
        SharedPreferences share = mContext.getSharedPreferences(LoginActivity.MYPREFERENCES, Context.MODE_PRIVATE);
        String token = share.getString("token", null);
        if (token != null){
            try {
                URL url = new URL(URL_GET_VPS);
                URLConnection urlConn = url.openConnection();
                if (!(urlConn instanceof HttpURLConnection)) {
                    throw new IOException("URL is not an Http URL");
                }
                HttpURLConnection httpConn = (HttpURLConnection) urlConn;
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestProperty("Content-Type", "application/json");
                httpConn.setRequestMethod("POST");
                httpConn.connect();
                JSONObject dataJson = new JSONObject();
                dataJson.put("auth_token", token);
                OutputStreamWriter out = new   OutputStreamWriter(httpConn.getOutputStream());
                out.write(dataJson.toString());
                out.close();
                String data = null;
                StringBuilder sb = new StringBuilder();
                response.setHttpCode(httpConn.getResponseCode());
                if (httpConn.getResponseCode() == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            httpConn.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null)
                        sb.append(line + "\n");
                    br.close();
                    response.setData(sb.toString());
                }
            } catch (IOException | JSONException e ) {
                e.printStackTrace();
                response.setHttpCode(500);
            }
        }else{
            response.setHttpCode(500);
        }
        return response;
    }

    public static Response getData(Context mContext, String instance_id) {
        Response response = new Response();
        SharedPreferences share = mContext.getSharedPreferences(LoginActivity.MYPREFERENCES, Context.MODE_PRIVATE);
        String token = share.getString("token", null);
        try {
            URL url = new URL(DOMAIN);
            URLConnection urlConn = url.openConnection();
            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("X-Auth-Token", token);
            httpConn.setRequestMethod("POST");
            httpConn.connect();
            JSONObject dataJson = new JSONObject();
            dataJson.put("instance_id", instance_id);
            dataJson.put("last_time", 60);
            OutputStreamWriter out = new   OutputStreamWriter(httpConn.getOutputStream());
            out.write(dataJson.toString());
            out.close();
            String data = null;
            StringBuilder sb = new StringBuilder();
            response.setHttpCode(httpConn.getResponseCode());
            if (httpConn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null)
                    sb.append(line + "\n");
                br.close();
                data = sb.toString();
                response.setData(data);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            response.setHttpCode(500);
        }
        return response;
    }

    public static Response getByMetric(Context mContext, String instance_id, String metric) {
        Response response = new Response();
        SharedPreferences share = mContext.getSharedPreferences(LoginActivity.MYPREFERENCES, Context.MODE_PRIVATE);
        String token = share.getString("token", null);
        String expire = share.getString("expire", null);
        try {
            URL url = new URL(DOMAIN_GET_METRIC);
            URLConnection urlConn = url.openConnection();
            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("X-Auth-Token", token);
            httpConn.setRequestMethod("POST");
            httpConn.connect();
            JSONObject dataJson = createJsonGetMetric(instance_id, 60, metric,expire);
            OutputStreamWriter out = new   OutputStreamWriter(httpConn.getOutputStream());
            out.write(dataJson.toString());
            out.close();
            String data = null;
            StringBuilder sb = new StringBuilder();
            response.setHttpCode(httpConn.getResponseCode());
            if (httpConn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null)
                    sb.append(line + "\n");
                br.close();
                data = sb.toString();
                response.setData(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.setHttpCode(500);
        }
        return response;
    }

    private static JSONObject createJsonGetMetric(String instance_id, int last_time, String metric,
                                                  String expire_date){
        JSONObject dataJson = new JSONObject();
        try{
            dataJson.put("expire_date",expire_date);
            dataJson.put("instance_id", instance_id);
            dataJson.put("last_time", last_time);
            dataJson.put("metric",metric);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataJson;
    }
}