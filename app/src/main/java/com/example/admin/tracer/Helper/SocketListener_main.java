package com.example.admin.tracer.Helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.tracer.EmailVerifyActivity;
import com.example.admin.tracer.MainActivity;
import com.example.admin.tracer.OpeningActivity;
import com.example.admin.tracer.R;
import com.example.admin.tracer.config.ImageOption;
import com.github.nkzawa.emitter.Emitter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2016-07-28.
 */
public class SocketListener_main {
    Activity mainActivity;
    private Handler mHandler = new Handler();

    private Emitter.Listener mainListener = new Emitter.Listener() {
        String result;
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            try{
               result = data.getString("URL");
                Thread t = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        mHandler.post(new Runnable(){
                            @Override
                            public void run() {
                                ImageLoader imageLoader = ImageLoader.getInstance();
                                ImageAware imageAware = new ImageViewAware((ImageView)mainActivity.findViewById(R.id.temp111), false);
                                imageLoader.displayImage(result, imageAware, ImageOption.options);
                                TextView temp  = (TextView)mainActivity.findViewById(R.id.temp000);
                                temp.setText(result);
                            }
                        });
                    }
                });
                t.start();
            } catch (JSONException e){
                System.out.println(e);
            }
        }
    };

    public void setActivity(Activity a) {
        this.mainActivity = a;
    }

    public Emitter.Listener getListener(){
        return mainListener;
    }
}
