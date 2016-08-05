package com.example.admin.tracer.Adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.tracer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2016-08-05.
 */
public class Main_sub1_grid_adapter extends BaseAdapter{

    Context mContext;
    JSONArray jsonArrayGroup;
    JSONArray jsonArrayProfileURL;
    int layout;
    LayoutInflater inf;

    public Main_sub1_grid_adapter(Context context , int layout){
        this.layout = layout;
        mContext = context;
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(jsonArrayGroup==null){
            return 0;
        } else {
            return jsonArrayGroup.length();
        }
    }

    @Override
    public Object getItem(int position) {
        if(jsonArrayGroup==null){
            return null;
        } else {
            try {
                return jsonArrayGroup.get(position);
            } catch (JSONException e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = inf.inflate(layout, null);
        ImageView iv = (ImageView)convertView.findViewById(R.id.imageView1);
        System.out.println(jsonArrayProfileURL);
      String tempURL;
        try {
            tempURL = jsonArrayProfileURL.getJSONObject(0).getString("profile");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
       Glide.with(mContext).load(tempURL).into(iv);
        return convertView;
    }

    public void setGroup(JSONArray jsonArrayGroup , JSONArray jsonArrayProfileURL){
        this.jsonArrayGroup = jsonArrayGroup;
        this.jsonArrayProfileURL = jsonArrayProfileURL;
    }

    public void addGroup(JSONObject jsonObject){
        this.jsonArrayGroup.put(jsonObject);
    }
}
