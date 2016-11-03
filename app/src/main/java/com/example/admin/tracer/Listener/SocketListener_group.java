package com.example.admin.tracer.Listener;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.admin.tracer.Adapter.Group_first_adapter;
import com.example.admin.tracer.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by admin on 2016-10-17.
 */
public class SocketListener_group {

    private RecyclerView mRecyclerView;
    private Group_first_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Activity a;

    private Emitter.Listener groupFileListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if(args[0].equals(false)){
                //에러처리
            } else {
                final JSONArray list = (JSONArray)args[0];
                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView = (RecyclerView)a.findViewById(R.id.group_list);
                        mRecyclerView.setHasFixedSize(false);
                        mAdapter = new Group_first_adapter(list);
                        mLayoutManager = new LinearLayoutManager(a);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.scrollToPosition(mAdapter.getItemCount()-1);
                    }
                });
            }
        }
    };

    private Emitter.Listener addFileResultListener = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            if(args[0].equals(false)){
                //에러처리
            } else{
                final JSONObject target = (JSONObject)args[0];
                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.addItem(target);
                    }
                });
            }
        }
    };

    public Emitter.Listener getGroupFileListener(){
        return groupFileListener;
    }
    public Emitter.Listener getAddFileResultListener(){return addFileResultListener;}

    public void setActivity(Activity a){
        this.a = a;
    }
}
