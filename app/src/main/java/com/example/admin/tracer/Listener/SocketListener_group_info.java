package com.example.admin.tracer.Listener;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.example.admin.tracer.Adapter.Group_info_adapter;
import com.example.admin.tracer.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;

/**
 * Created by admin on 2016-11-03.
 */
public class SocketListener_group_info {

    private ViewPager mViewPager;
    private Group_info_adapter mPagerAdapter;

    FragmentActivity a;

    private Emitter.Listener groupInfoListener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if(args[0].equals(false)){
                //내부서버 오류
            } else{
                a.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mViewPager = (ViewPager)a.findViewById(R.id.group_info_pager);
                        mPagerAdapter = new Group_info_adapter(a.getSupportFragmentManager());
                        mPagerAdapter.setData((JSONArray)args[0] , (int)args[1]);
                        mViewPager.setAdapter(mPagerAdapter);
                    }
                });

            }
        }
    };

    public void setActivity (Activity a){
        this.a = (FragmentActivity)a;
    }


    public Emitter.Listener getGroupInfoListener() {
        return groupInfoListener;
    }


}
