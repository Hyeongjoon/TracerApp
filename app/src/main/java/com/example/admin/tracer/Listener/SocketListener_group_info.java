package com.example.admin.tracer.Listener;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.github.nkzawa.emitter.Emitter;

/**
 * Created by admin on 2016-11-03.
 */
public class SocketListener_group_info {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    private Emitter.Listener groupInfoListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };


    public Emitter.Listener getGroupInfoListener() {
        return groupInfoListener;
    }

    private class PagerAdapter extends FragmentStatePagerAdapter{

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}
