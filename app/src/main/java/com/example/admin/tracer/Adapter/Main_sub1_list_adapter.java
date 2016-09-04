package com.example.admin.tracer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
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
public class Main_sub1_list_adapter extends BaseAdapter implements View.OnTouchListener {

    Context mContext;
    JSONArray jsonArrayGroup;
    int groupNum;
    int layout;
    LayoutInflater inf;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ViewHolder vh = (ViewHolder) v.getTag();
        vh.lastTouchedX = event.getX();
        vh.lastTouchedY = event.getY();
        return false;
    }

    public static class ViewHolder {
        public RelativeLayout relativeLayout;
        public float lastTouchedX;
        public float lastTouchedY;
        public ViewHolder(View v) {
            relativeLayout = (RelativeLayout) v;
        };
    }

    public Main_sub1_list_adapter(Context context , int layout){
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
                return groupNum;
        }
    }

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
    }

    @Override
    public long getItemId(int position) {
        if(jsonArrayGroup == null){
            return 0;
        } else{
            JSONObject targetJSON = (JSONObject)getItem(position);
            try {
                return targetJSON.getInt("gid");
            } catch (JSONException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

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
    }

    public void setGroup(JSONArray jsonArrayGroup ,  int groupNum) {
        this.jsonArrayGroup = jsonArrayGroup;
        this.groupNum = groupNum;
    }

    public void addGroup(JSONObject jsonObject){
        this.jsonArrayGroup.put(jsonObject);
    }
}
