package com.example.admin.tracer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by admin on 2016-08-10.
 */

public class Main_sub2_grid_adapter extends BaseAdapter {
    Context mContext;
    int layout;
    LayoutInflater inf;
    JSONObject alram;
    JSONArray location;
    JSONArray like_location;
    JSONArray dislike_location;
    JSONArray reply;
    JSONArray re_reply;
    JSONArray like_reply;
    JSONArray like_re_reply;

    public Main_sub2_grid_adapter(Context context , int layout){
        this.layout = layout;
        this.mContext = context;
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void setAlram(JSONObject alram){
        this.alram = alram;
        //배열로 하나 모아서 해놓을듯
    }

    private int getObjectNum(){
        return 0;
    }
}
