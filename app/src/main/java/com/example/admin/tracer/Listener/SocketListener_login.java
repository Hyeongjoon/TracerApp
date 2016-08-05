package com.example.admin.tracer.Listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;

import com.example.admin.tracer.EmailVerifyActivity;
import com.example.admin.tracer.MainActivity;
import com.example.admin.tracer.OpeningActivity;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2016-07-13.
 */

public class SocketListener_login {
    private String result;
    private ProgressDialog pDialog;
    private Dialog dialog;
    private Handler mHandler = new Handler();
    private Activity loginActivity;

    private Emitter.Listener resultLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            try{
                result = data.getString("result");
                if(result.equals("true")){
                    pDialog.cancel();
                    Intent intent = new Intent(loginActivity , MainActivity.class);
                    loginActivity.startActivity(intent);
                    loginActivity.finish();
                    OpeningActivity.OpeningActivity.finish();
                    return;
                } else if(result.equals("verify")){
                    pDialog.cancel();
                    String email = data.getString("email");
                    Intent intent = new Intent(loginActivity , EmailVerifyActivity.class);
                    intent.putExtra("email" , email);
                    loginActivity.startActivity(intent);
                    loginActivity.finish();
                    return;
                }else{
                    Thread t = new Thread(new Runnable(){
                        @Override
                        public void run() {
                            mHandler.post(new Runnable(){
                                @Override
                                public void run() {
                                    // 알림창 띄우기
                                    createDialog(loginActivity);
                                    dialog.show();
                                    pDialog.cancel();
                                }
                            });
                        }
                    });
                    t.start();
                }
            } catch (JSONException e){
                System.out.println(e);
            }
        }
    };

    public void setPDialog(ProgressDialog p){
        pDialog = p;
    }

    public void setDialogActivity (Activity a){
        this.loginActivity = a;
        return;
    }

    private void createDialog(Activity a){
        AlertDialog.Builder builder = new AlertDialog.Builder(a);     // 여기서 this는 Activity의 this
        // 여기서 부터는 알림창의 속성 설정
        builder.setTitle("로그인 오류")        // 제목 설정
                .setMessage("아이디나 비밀번호가 잘못됐습니다.")        // 메세지 설정
                .setCancelable(true)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    // 확인 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton){
                        dialog.cancel();
                    }
                });
        dialog = builder.create();
        // 알림창 띄우기
    }
    public Emitter.Listener getListener(){
        return resultLogin;
    }
}