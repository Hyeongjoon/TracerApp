package com.example.admin.tracer.Listener;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.admin.tracer.Adapter.Main_sub2_grid_adapter;
import com.example.admin.tracer.Helper.TimeConvert;
import com.example.admin.tracer.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by admin on 2016-08-10.
 */
public class SocketListener_main_sub2 {
    Main_sub2_grid_adapter adapter;
    Activity a;

    private Emitter.Listener mainSub2Listener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

                JSONObject alram = (JSONObject)args[0];
                adapter.setAlram(alram);
                /*JSONArray location = alram.getJSONArray("location");
                JSONArray like_location = alram.getJSONArray("like");
                JSONArray dislike_location = alram.getJSONArray("dislike");
                JSONArray reply = alram.getJSONArray("reply");
                JSONArray re_reply = alram.getJSONArray("re_reply");
                JSONArray like_reply = alram.getJSONArray("like_reply");
                JSONArray like_re_reply = alram.getJSONArray("like_re_reply");*/

                //System.out.println(re_reply.length());
                //JSONObject temp = location.getJSONObject(0);
                //String time = temp.getString("updated_time");
                //TimeConvert.calculateGoingTime(time);

        }
    };

    public void setActivity(Activity a){
        this.a = a;
        adapter = new Main_sub2_grid_adapter(a.getApplicationContext() , R.layout.main_sub2_item);
    }

    public Emitter.Listener getListener(){
        return this.mainSub2Listener;
    }
}
