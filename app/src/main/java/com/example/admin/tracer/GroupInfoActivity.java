package com.example.admin.tracer;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.admin.tracer.Listener.SocketIO;
import com.example.admin.tracer.Listener.SocketListener_group_info;

/**
 * Created by admin on 2016-10-31.
 */
public class GroupInfoActivity extends FragmentActivity{

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    private SocketListener_group_info socketListener_group_info = new SocketListener_group_info();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

    }

    @Override
    protected void onStart(){
        super.onStart();
        SocketIO.getSocket().on("dateFileResult" , socketListener_group_info.getGroupInfoListener());

    }

    @Override
    protected  void onStop(){
        super.onStop();
        SocketIO.getSocket().off("dateFileResult" , socketListener_group_info.getGroupInfoListener());
    }
}
