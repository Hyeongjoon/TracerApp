package com.example.admin.tracer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.tracer.GroupInfoActivity;
import com.example.admin.tracer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016-08-05.
 */
public class Main_sub1_list_adapter extends RecyclerView.Adapter <Main_sub1_list_adapter.ViewHolder>  {

    List<JSONObject> mList= new ArrayList<>();;


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView groupImage;
        public TextView groupName;
        public TextView groupList;
        public TextView groupNum;
        public LinearLayout linearLayout;
        public ViewHolder(View v) {
            super(v);
            linearLayout = (LinearLayout)v.findViewById(R.id.main_sub1_item);
            groupImage = (ImageView)v.findViewById(R.id.main_sub1_imageView);
            groupName = (TextView)v.findViewById(R.id.main_sub1_group_name);
            groupList = (TextView)v.findViewById(R.id.main_sub1_member_list);
            groupNum = (TextView)v.findViewById(R.id.main_sub1_group_num);
        };

        public void setData(JSONObject group){
            try {
                groupNum.setText(group.getString("member_number"));
                groupName.setText(group.getString("name"));
                groupList.setText( group.getString("memberName"));
                if(!group.getString("file_location").equals(null)){
                    String imageURL = group.getString("file_location");
                    Glide.with(groupImage.getContext()).load(imageURL).diskCacheStrategy(DiskCacheStrategy.ALL).into(groupImage);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public Main_sub1_list_adapter( JSONArray jsonArrayGroup ){
        for(int i =0 ; i<jsonArrayGroup.length() ; i++){
            try {
                mList.add(jsonArrayGroup.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_sub1_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            JSONObject group= mList.get(position);
            holder.setData(group);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



    @Override
    public long getItemId(int position) {
        if(mList.size()==0){
            return 0;
        } else{
            try {
                JSONObject targetJSON = mList.get(position);
                return targetJSON.getInt("gid");
            } catch (JSONException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(mList.size()==0){
            return 0;
        } else {
            return  mList.size();
        }
    }

    public List getDataList(){
        return mList;
    }

    public List getDataOrder(){
        List temp = new ArrayList();
        for(int i =0 ; i<mList.size() ; i++){
            try {
                temp.add(mList.get(i).getString("gid"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }

    public void addGroup(JSONObject jsonObject){
        mList.add(0 , jsonObject);
    }

    public void deleteGroup(int position){

    }
}
