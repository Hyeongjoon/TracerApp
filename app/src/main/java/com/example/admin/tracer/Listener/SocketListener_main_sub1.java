package com.example.admin.tracer.Listener;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


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
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.post( new Runnable() {
                            @Override
                            public void run() {
                                GridView gridView_group = (GridView)a.findViewById(R.id.main_sub1_group_grid);
                                adapter.setGroup(group , url , groupNumJSON , groupInfo);
                                gridView_group.setAdapter(adapter);
                                gridView_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Log.d("msg" , ""+id);
                                        //여기에 인텐트 넘어가는거 넣으면 될듯
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
