package com.example.admin.tracer.Adapter;

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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.admin.tracer.GroupInfoActivity;
import com.example.admin.tracer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2016-08-05.
 */
public class Main_sub1_list_adapter extends RecyclerView.Adapter <Main_sub1_list_adapter.ViewHolder> implements View.OnTouchListener {

    JSONArray jsonArrayGroup;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ViewHolder vh = (ViewHolder) v.getTag();
        vh.lastTouchedX = event.getX();
        vh.lastTouchedY = event.getY();
        return false;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView groupImage;
        public TextView groupName;
        public TextView groupList;
        public TextView groupNum;
        public LinearLayout linearLayout;
        public float lastTouchedX;
        public float lastTouchedY;
        public ViewHolder(View v) {
            super(v);
            linearLayout = (LinearLayout)v.findViewById(R.id.main_sub1_item);
            groupImage = (ImageView)v.findViewById(R.id.main_sub1_imageView);
            groupName = (TextView)v.findViewById(R.id.main_sub1_group_name);
            groupList = (TextView)v.findViewById(R.id.main_sub1_group_list);
            groupNum = (TextView)v.findViewById(R.id.main_sub1_group_num);
        };
    }

    public Main_sub1_list_adapter( JSONArray jsonArrayGroup ){
        this.jsonArrayGroup = jsonArrayGroup;
    }
    /*
    @Override
    public Object getItem(int position) {
        if( jsonArrayGroup==null){
            return null;
        } else {
            try {
                return jsonArrayGroup.getJSONObject(position);
            } catch (JSONException e){
                e.printStackTrace();
                return null;
            }
        }
    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_sub1_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Main_sub1_list_adapter.ViewHolder vh = (Main_sub1_list_adapter.ViewHolder) view.getTag();
                        final int touchedX = (int) (vh.lastTouchedX + 0.5f);
                        final int touchedY = (int) (vh.lastTouchedY + 0.5f);

                        view.startDrag(null, new View.DragShadowBuilder(view) {

                            @Override
                            public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
                                super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
                                shadowTouchPoint.x = touchedX;
                                shadowTouchPoint.y = touchedY;
                            }

                            @Override
                            public void onDrawShadow(Canvas canvas) {
                                super.onDrawShadow(canvas);
                            }
                        }, view, 0);

                        view.setVisibility(View.INVISIBLE);
                        return true;
                    }
                };
                return true;
            }
        });
    }

    @Override
    public long getItemId(int position) {
        if(jsonArrayGroup == null){
            return 0;
        } else{
            try {
                JSONObject targetJSON = (JSONObject)jsonArrayGroup.getJSONObject(position);
                return targetJSON.getInt("gid");
            } catch (JSONException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(jsonArrayGroup==null){
            return 0;
        } else {
            return  jsonArrayGroup.length();
        }
    }
    /*
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null) {
            convertView = inf.inflate(layout, null);
            convertView.setOnTouchListener(this);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        JSONObject target = (JSONObject)getItem(position);
        try {
                TextView groupNum = (TextView)convertView.findViewById(R.id.main_sub1_group_num);
                TextView groupName = (TextView)convertView.findViewById(R.id.main_sub1_group_name);
                TextView groupMember = (TextView)convertView.findViewById(R.id.main_sub1_member_list);

                groupNum.setText(target.getString("member_number"));
                groupName.setText(target.getString("name"));
                groupMember.setText(target.getString("memberName"));
           if(!target.getString("file_location").equals(null)){
                String imageURL = target.getString("file_location");
                Glide.with(mContext).load(imageURL).into((ImageView)convertView.findViewById(R.id.main_sub1_imageView));
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return convertView;
    }*/

    public void addGroup(JSONObject jsonObject){
        this.jsonArrayGroup.put(jsonObject);
    }
}
