package com.example.admin.tracer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by admin on 2016-10-06.
 */
public class GroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Intent intent =getIntent();
        int temp = intent.getExtras().getInt("gid");
        TextView textView = (TextView)findViewById(R.id.groupGid);
        Log.d("msg" , temp + "");
        textView.setText(temp+"");

    }

    @Override
    protected void onStart(){
        super.onStart();

    }
}
