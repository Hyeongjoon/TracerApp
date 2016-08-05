package com.example.admin.tracer;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.tracer.Listener.SocketIO;
import com.example.admin.tracer.Listener.SocketListener_signUp;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {
    SocketListener_signUp socketListener = null;
    ProgressDialog pDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        socketListener =new SocketListener_signUp();
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    protected void onStart(){
        SocketIO.getSocket().on("signUp_result" , socketListener.getListener());
        socketListener.setActivity(this);
        super.onStart();
    }

    public void signUp_buttonClick(View v){
        switch (v.getId()) {
            case R.id.signUp_signUp : {
                EditText emailText = (EditText)findViewById(R.id.signUp_email);
                EditText passwordText = (EditText)findViewById(R.id.signUp_password);
                EditText password_confirmText = (EditText)findViewById(R.id.signUp_password_confirm);
                EditText nameText = (EditText)findViewById(R.id.signUp_name);
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String password_confirm = password_confirmText.getText().toString().trim();
                String name = nameText.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+$";

                if(!email.matches(emailPattern)){
                    Toast.makeText(getApplicationContext() , "invalid email" ,Toast.LENGTH_SHORT).show();
                    return;
                } else if(password.length()<8 ||password.length()>16){
                    Toast.makeText(getApplicationContext() , "비밀번호는 8자리 이상 16자리 이하로 해주세요" ,Toast.LENGTH_SHORT).show();
                    return;
                } else if(!password.equals(password_confirm)){
                    Toast.makeText(getApplicationContext() , "password and password_confirm is differnt" ,Toast.LENGTH_SHORT).show();
                    return;
                } else if(name.length()==0){
                    Toast.makeText(getApplicationContext() , "invalid name" ,Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    JSONObject inform = new JSONObject();
                    try{
                        inform.put("email" , email);
                        inform.put("password" , password);
                        inform.put("password_confirm" , password_confirm);
                        inform.put("name" , name);
                    } catch (JSONException e){
                        System.out.println(e);
                    }
                    pDialog= ProgressDialog.show(this, "signUp.....", "Please wait", true, true);
                    socketListener.setpDialog(pDialog);
                    SocketIO.getSocket().emit("signUp", inform);
                    return;
                }
                }
        }
    }

    @Override
    protected void onDestroy(){
        SocketIO.getSocket().off("signUp_result" , socketListener.getListener());
        super.onDestroy();
    }
}
