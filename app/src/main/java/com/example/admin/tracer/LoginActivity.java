package com.example.admin.tracer;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.tracer.Helper.SocketIO;
import com.example.admin.tracer.Helper.SocketListener_login;

import org.json.JSONException;
import org.json.JSONObject;



public class LoginActivity extends AppCompatActivity {
    SocketListener_login socketListener = null;
    ProgressDialog pDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        socketListener =new SocketListener_login();
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void onStart(){
        super.onStart();
        SocketIO.getSocket().on("login_result" , socketListener.getListener());
        socketListener.setDialogActivity(this);
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
    public void onDestroy(){
        SocketIO.getSocket().off("login_result" , socketListener.getListener());
        super.onDestroy();

    }
}

