package com.example.admin.tracer.Listener;

import android.app.Activity;
import android.os.Handler;
import android.widget.ListView;

import com.example.admin.tracer.Adapter.Main_sub2_list_adapter;
import com.example.admin.tracer.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2016-08-10.
 */
public class SocketListener_main_sub2 {
    Main_sub2_list_adapter adapter;
    Activity a;
    private Handler mHandler = new Handler();

    private Emitter.Listener mainSub2Listener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            try {
                final JSONObject alram = (JSONObject) args[0];
                JSONObject order = (JSONObject) args[1];
                final JSONArray temp = order.getJSONArray("order");
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.post( new Runnable() {
                            @Override
                            public void run() {
                                adapter.setAlram(alram , temp);
                                ListView mainsub2_listView = (ListView) a.findViewById(R.id.main_sub2_listview);
                                mainsub2_listView.setAdapter(adapter);
                            }
                        });
                    }
                });
                t.start();
            } catch (JSONException e){
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            };
        }
    };

    public void setActivity(Activity a){
        this.a = a;
        adapter = new Main_sub2_list_adapter(a.getApplicationContext() , R.layout.main_sub2_item);
    }

    public Emitter.Listener getListener(){
        return this.mainSub2Listener;
    }
}
