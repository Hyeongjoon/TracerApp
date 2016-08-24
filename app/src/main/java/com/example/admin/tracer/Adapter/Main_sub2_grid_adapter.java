package com.example.admin.tracer.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.tracer.Helper.AlramHelpler;
import com.example.admin.tracer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2016-08-10.
 */

public class Main_sub2_grid_adapter extends BaseAdapter {
    Context mContext;
    int layout;
    LayoutInflater inf;
    JSONArray resultArr = new JSONArray();
    JSONArray order;

    public Main_sub2_grid_adapter(Context context , int layout){
        this.layout = layout;
        this.mContext = context;
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(order==null){
            return 0;
        } else {
        return order.length();
        }
    }

    @Override
    public Object getItem(int position) {
        try {
            for (int i = 0; i < order.length(); i++) {
                if (order.getInt(position) == resultArr.getJSONObject(i).getInt("aid")) {
                    return resultArr.getJSONObject(i);
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(order == null){
            return -1;
        } else {
            try {
                return (long)order.getInt(position);
            } catch (JSONException e){
                e.printStackTrace();
                return -1;
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = inf.inflate(layout, null);
        if(getCount()==0){
            convertView = inf.inflate(layout, null);
            return convertView;
        }
            TextView name = (TextView) convertView.findViewById(R.id.main_sub2_profileName);
            TextView time = (TextView) convertView.findViewById(R.id.main_sub2_time);
            TextView content = (TextView) convertView.findViewById(R.id.main_sub2_content);
            JSONObject target = (JSONObject) getItem(position);
            try {
                name.setText(target.getString("alramName"));
                time.setText(target.getString("time"));
                content.setText(target.getString("contents"));
                String tempURL = target.getString("profile");
                if(!tempURL.equals("")){
                    Glide.with(mContext).load(tempURL).into((ImageView)convertView.findViewById(R.id.main_sub2_profile_image));
                }
            } catch (JSONException e) {
                return null;
            }
            return convertView;
    }

    public void setAlram(JSONObject alram , JSONArray order){
        try {
            this.order = order;
            for(int i = 0 ; i <alram.getJSONArray("location").length() ; i++){
                resultArr.put(alram.getJSONArray("location").get(i));
            }
            for(int i = 0 ; i <alram.getJSONArray("like").length() ; i++){
                resultArr.put(alram.getJSONArray("like").get(i));
            }
            for(int i = 0 ; i <alram.getJSONArray("dislike").length() ; i++){
                resultArr.put(alram.getJSONArray("dislike").get(i));
            }
            for(int i = 0 ; i <alram.getJSONArray("reply").length() ; i++){
                resultArr.put(alram.getJSONArray("reply").get(i));
            }
            for(int i = 0 ; i <alram.getJSONArray("re_reply").length() ; i++){
                resultArr.put(alram.getJSONArray("re_reply").get(i));
            }
            for(int i = 0 ; i <alram.getJSONArray("like_reply").length() ; i++){
                resultArr.put(alram.getJSONArray("like_reply").get(i));
            }
            for(int i = 0 ; i <alram.getJSONArray("like_re_reply").length() ; i++){
                resultArr.put(alram.getJSONArray("like_re_reply").get(i));
            }//resultArr 셋팅

            for(int i = 0 ; i < resultArr.length(); i++){
                resultArr.getJSONObject(i).put("time" , AlramHelpler.calculateGoingTime(resultArr.getJSONObject(i).getString("updated_time")));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
