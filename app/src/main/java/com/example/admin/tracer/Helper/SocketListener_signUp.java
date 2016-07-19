package com.example.admin.tracer.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;


import com.example.admin.tracer.EmailVerifyActivity;
import com.example.admin.tracer.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2016-07-14.
 */

public class SocketListener_signUp {
    private String result;
    private ProgressDialog pDialog;
    private Dialog dialog;
    private Handler mHandler = new Handler();
    private Activity activity = null;

                        private Emitter.Listener resultSignUp = new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                JSONObject data = (JSONObject) args[0];
                                try{
                                    result = data.getString("result");
                                    if(result.equals("true")){
                                        pDialog.cancel();
                                        Intent intent = new Intent(
                                                activity.getApplicationContext() , // 현재 화면의 제어권자
                                                EmailVerifyActivity.class);
                                        EditText emailText = (EditText)activity.findViewById(R.id.signUp_email);
                                        String email = emailText.getText().toString().trim();
                                        intent.putExtra("email" , email);
                                        activity.startActivity(intent);
                                        activity.finish();
                                    } else {
                                        Thread t = new Thread(new Runnable(){
                                            @Override
                                            public void run() {
                                                mHandler.post(new Runnable(){
                                                    @Override
                                                    public void run() {
                                                        createDialog(activity , result);
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
                return;
            }
        }
    };
    public Emitter.Listener getListner(){
        return resultSignUp;
    }

    public void setpDialog(ProgressDialog pDialog){
        this.pDialog = pDialog;
        return;
    }


    public void setActivity (Activity a){
        this.activity = a;
        System.out.println("여기가 그거임 로그ㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅㅅ");
        return;
    }
    private void createDialog(Activity a , String result ){
        AlertDialog.Builder builder = new AlertDialog.Builder(a);     // 여기서 this는 Activity의 this
        // 여기서 부터는 알림창의 속성 설정
        builder.setTitle("회원가입 오류")        // 제목 설정
                .setMessage(result)        // 메세지 설정
                .setCancelable(true)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    // 확인 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton){
                        dialog.cancel();
                    }
                });
        dialog = builder.create();    // 알림창 객체 생성
        // 알림창 띄우기
    }


}
