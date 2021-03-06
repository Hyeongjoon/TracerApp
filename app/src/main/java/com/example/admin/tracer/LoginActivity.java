package com.example.admin.tracer;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.admin.tracer.Listener.SocketIO;
import com.example.admin.tracer.Listener.SocketListener_login;

import org.json.JSONException;
import org.json.JSONObject;



public class LoginActivity extends AppCompatActivity {
    SocketListener_login socketListener = null;
    ProgressDialog pDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        socketListener = new SocketListener_login();
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onStart(){
        super.onStart();
        socketListener.setDialogActivity(this);
        SocketIO.getSocket().on("login_result" , socketListener.getListener());

    }

    public void Login_ButtonClick(View v) {
        switch (v.getId()) {
            case R.id.login_login: {
                EditText emailText = (EditText)findViewById(R.id.login_email);
                EditText passwordText = (EditText)findViewById(R.id.login_password);
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+";
                if(!email.matches(emailPattern)){
                    Toast.makeText(getApplicationContext() , "invalid email" ,Toast.LENGTH_SHORT).show();
                    return;
                } else if(password.length()==0){
                    Toast.makeText(getApplicationContext() , "password is empty" ,Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    JSONObject inform = new JSONObject();
                    try{
                        inform.put("email" , email);
                        inform.put("password" , password);
                    } catch (JSONException e){
                        System.out.println(e);
                    }
                    pDialog = ProgressDialog.show(this, "login.....", "Please wait", true, true);
                    socketListener.setPDialog(pDialog);
                    SocketIO.getSocket().emit("login", inform);
                    return;
                }
            }
        }
    }
    @Override
    public void onStop(){
        super.onStop();
        SocketIO.getSocket().off("signUp_result" , socketListener.getListener());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        findViewById(R.id.login_background).setBackground(null);
    }
}

