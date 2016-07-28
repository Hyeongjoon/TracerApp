package com.example.admin.tracer.Helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 2016-07-27.
 */
public class ReqImage extends AsyncTask<String, Integer,Bitmap> {
    Bitmap bmImg;
    @Override
    protected Bitmap doInBackground(String... urls) {
        // TODO Auto-generated method stub
        try{
            URL myFileUrl = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();

            bmImg = BitmapFactory.decodeStream(is);


        }catch(IOException e){
            e.printStackTrace();
        }
        return bmImg;
    }

    protected void onPostExecute(ImageView imv){
        imv.setImageBitmap(bmImg);
    }

}
