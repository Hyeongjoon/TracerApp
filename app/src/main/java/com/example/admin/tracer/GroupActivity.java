package com.example.admin.tracer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.tracer.Listener.SocketIO;
import com.example.admin.tracer.Listener.SocketListener_group;

/**
 * Created by admin on 2016-10-06.
 */

public class GroupActivity extends AppCompatActivity {

    SocketListener_group socketListener = new SocketListener_group();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Intent intent =getIntent();
        int temp = intent.getIntExtra("gid" , 0);
        SocketIO.getSocket().emit("getGrInfo" , temp);
    }

    @Override
    protected void onStart(){
        super.onStart();
        socketListener.setActivity(this);
        SocketIO.getSocket().on("getGroupFile" , socketListener.getGroupFileListener());
    }




    @Override
    protected void onStop() {
        super.onStop();
        SocketIO.getSocket().off("getGroupFile" , socketListener.getGroupFileListener());
    }
}
