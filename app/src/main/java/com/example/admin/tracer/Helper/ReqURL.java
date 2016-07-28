package com.example.admin.tracer.Helper;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 2016-07-27.
 */
public class ReqURL extends AsyncTask<String, Void,String> {
    @Override
    protected String doInBackground(String... params) {

        OutputStream os   = null;
        InputStream is   = null;
        ByteArrayOutputStream baos = null;

        try {
            URL myFileUrl = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject job = new JSONObject();
            job.put("phoneNum", "01000000000");
            job.put("name", "test name");
            job.put("address", "test address");

            os = conn.getOutputStream();
            os.write(job.toString().getBytes());
            os.flush();

            conn.connect();
//            is = conn.getInputStream();
/*
            int responseCode = conn.getResponseCode();
            String response;
            if(responseCode == HttpURLConnection.HTTP_OK) {

                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                response = new String(byteData);

                JSONObject responseJSON = new JSONObject(response);
                Boolean result = (Boolean) responseJSON.get("result");
                String age = (String) responseJSON.get("age");
                String job1 = (String) responseJSON.get("job");

            }*/

        } catch (IOException e){
            e.getStackTrace();
        } catch (JSONException e){
            e.getStackTrace();
        }
        return null;
    }
}
