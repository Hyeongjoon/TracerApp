package com.example.admin.tracer.Helper;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.admin.tracer.MainActivity;
import com.example.admin.tracer.R;
import com.github.nkzawa.emitter.Emitter;


import org.json.JSONArray;

import org.json.JSONObject;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by admin on 2016-07-28.
 */
public class SocketListener_main_sub1 {

    String result;
    String result2;
    Activity a;
    int groupNum;
    Bitmap b;

    private Handler mHandler = new Handler();

    public void setActivity(Activity a){
        this.a = a;
    }

    private Emitter.Listener mainSub1Listener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONArray url = (JSONArray) args[0];
            JSONArray group = (JSONArray) args[1];
            JSONObject groupNumJSON = (JSONObject)args[2];
            try{
                JSONObject data = url.getJSONObject(0);
                JSONObject data2 = url.getJSONObject(1);
                result = data.getString("profile");
                result2 = data2.getString("profile");
                groupNum = groupNumJSON.getInt("groupNum");
                Thread t = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        mHandler.post(new Runnable(){
                            @Override
                            public void run() {
                                RelativeLayout relativeLayout = (RelativeLayout)a.findViewById(R.id.main_sub1);
                                relativeLayout.removeView((ImageView)a.findViewById(R.id.favorite));
                                final ImageView iv = new ImageView(a);
                                final ImageView iv2 = new ImageView(a);
                                iv.setLayoutParams(new RelativeLayout.LayoutParams(300 ,300));
                                iv2.setLayoutParams(new RelativeLayout.LayoutParams(300, 300));
                                relativeLayout.addView(iv);
                                relativeLayout.addView(iv2);
                                Glide.with(a).load(result2).asBitmap().into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        b = resource;
                                        iv.setImageBitmap(b);
                                    }
                                });
                                Glide.with(a).load(result).asBitmap().into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        iv2.setImageBitmap(resource);
                                    }
                                });
                            }
                        });
                    }
                });
                t.start();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };

    public Emitter.Listener getListener(){
        return mainSub1Listener;
    }
}
