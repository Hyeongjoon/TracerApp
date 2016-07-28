package com.example.admin.tracer;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.admin.tracer.Helper.ReqURL;
import com.example.admin.tracer.Helper.SocketIO;
import com.example.admin.tracer.Helper.SocketListener_main;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by admin on 2016-07-25.
 */
public class MainActivity extends AppCompatActivity {
    SocketListener_main socketListener = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        socketListener = new SocketListener_main();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        SocketIO.getSocket().on("GroupImageResult" , socketListener.getListener());
        SocketIO.getSocket().emit("getGroupImage" , true);
        socketListener.setActivity(this);
        super.onStart();
    }

    public void temp(View v){

    }

    @Override
    protected void onDestroy(){
        SocketIO.getSocket().off("signUp_result" , socketListener.getListener());
        super.onDestroy();
    }

}
