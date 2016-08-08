package com.example.admin.tracer.Listener;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.admin.tracer.Adapter.Main_sub1_grid_adapter;
import com.example.admin.tracer.R;
import com.github.nkzawa.emitter.Emitter;


import org.json.JSONArray;

import org.json.JSONObject;

/**
 * Created by admin on 2016-07-28.
 */
public class SocketListener_main_sub1 {

    Main_sub1_grid_adapter adapter;
    Activity a;

    private Handler mHandler = new Handler();

    public void setActivity(Activity a){
        this.a = a;
        adapter = new Main_sub1_grid_adapter(a.getApplicationContext() , R.layout.main_sub1_item);
    }

    private Emitter.Listener mainSub1Listener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            final JSONArray url = (JSONArray) args[0];
            final JSONArray group = (JSONArray) args[1];
            final JSONObject groupNumJSON = (JSONObject)args[2];
            final JSONArray groupInfo = (JSONArray)args[3]; // 예외처리할것
            try{
                Thread t = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        mHandler.post(new Runnable(){
                            @Override
                            public void run() {
                                GridView gridView_group = (GridView)a.findViewById(R.id.main_sub1_group_grid);
                                gridView_group.setNumColumns(2);
                                adapter.setGroup(group , url , groupNumJSON , groupInfo);
                                gridView_group.setAdapter(adapter);
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
