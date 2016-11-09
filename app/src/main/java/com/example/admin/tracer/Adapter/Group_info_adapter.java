package com.example.admin.tracer.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.tracer.GroupInfoFragment;
import com.example.admin.tracer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2016-11-05.
 */
public class Group_info_adapter extends FragmentStatePagerAdapter {

    JSONArray jsonArray;
    int uid;

    public Group_info_adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        try {
            JSONObject data = jsonArray.getJSONObject(position);
            return  GroupInfoFragment.create(position , data , uid);
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getCount() {
        if(jsonArray==null){
            return 0;
        } else{
            return jsonArray.length();
        }
    }

    public void setData(JSONArray jsonArray , int uid){
        this.jsonArray = jsonArray;
        this.uid = uid;
    }
}
