package com.example.admin.tracer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.admin.tracer.Listener.SocketIO;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2016-07-15.
 */
public class EmailVerifyActivity extends AppCompatActivity {
    String email = "";
    Handler mHandler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
    }

    public void verifyEmail_Onclick(View v){
        switch (v.getId()) {
            case R.id.verify_login: {
                Intent intent = new Intent(this , LoginActivity.class);
                startActivity(intent);
                this.finish();
                return;
            }
            case R.id.verify_Reemail: {
                JSONObject inform = new JSONObject();
                try{
                    inform.put("email" , email);
                } catch (JSONException e){
                    System.out.println(e);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);     // 여기서 this는 Activity의 this
                // 여기서 부터는 알림창의 속성 설정
                builder.setTitle("인증 이메일 발송")        // 제목 설정
                        .setMessage("이메일이 발송 되었습니다.")        // 메세지 설정
                        .setCancelable(true)        // 뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            // 확인 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton){
                                dialog.cancel();
                            }
                        });
                final Dialog dialog = builder.create();    // 알림창 객체 생성
                // 알림창 띄우기
                Thread t = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        mHandler.post(new Runnable(){
                            @Override
                            public void run() {
                                dialog.show();
                            }
                        });
                    }
                });
                t.start();
                SocketIO.getSocket().emit("reEmail", inform);
                return;
            }
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        findViewById(R.id.email_background).setBackground(null);
    }
}
