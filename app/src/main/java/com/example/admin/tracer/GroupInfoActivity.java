package com.example.admin.tracer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import com.example.admin.tracer.Listener.SocketIO;
import com.example.admin.tracer.Listener.SocketListener_group_info;

/**
 * Created by admin on 2016-10-31.
 */
public class GroupInfoActivity extends FragmentActivity{

    private SocketListener_group_info socketListener_group_info = new SocketListener_group_info();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        socketListener_group_info.setActivity(this);
        Intent intent = getIntent();
        String date = intent.getStringExtra("d");
        long gid = intent.getLongExtra("gid" , -1);
        SocketIO.getSocket().emit("getDateFile" , gid , date);
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
