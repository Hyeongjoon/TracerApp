package com.example.admin.tracer.Listener;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.admin.tracer.Adapter.Group_first_adapter;
import com.example.admin.tracer.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;

/**
 * Created by admin on 2016-10-17.
 */
public class SocketListener_group {

    private RecyclerView mRecyclerView;
    private Group_first_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Activity a;

    private Emitter.Listener GroupFileListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if(args[0].equals(false)){
                //에러처리
            } else {
                final JSONArray list = (JSONArray)args[0];
                Log.d("msg" , list+"");
                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView = (RecyclerView) a.findViewById(R.id.group_list);
                        mAdapter = new Group_first_adapter(list);
                        mLayoutManager = new LinearLayoutManager(a);
                        Log.d("msg" , "여기까진 오냐?");
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });

            }
        }
    };

    public Emitter.Listener getGroupFileListener(){
        return GroupFileListener;
    }

    public void setActivity(Activity a){
        this.a = a;
    }
}
