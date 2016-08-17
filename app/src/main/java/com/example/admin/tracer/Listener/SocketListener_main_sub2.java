package com.example.admin.tracer.Listener;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.admin.tracer.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by admin on 2016-08-10.
 */
public class SocketListener_main_sub2 {

    Activity a;

    private Emitter.Listener mainSub2Listener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("msg" , "여기오냐");

        }
    };

    public void setActivity(Activity a){
        this.a = a;
    }

    public Emitter.Listener getListener(){
        return this.mainSub2Listener;
    }
}
