package com.example.admin.tracer.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;

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

    private Emitter.Listener resultLogin = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            try{
                result = data.getString("result");
                if(result.equals("true")){
                    pDialog.cancel();
                    //로그인 성공했을때 코드적으면 될듯
                } else{
                    Thread t = new Thread(new Runnable(){
                        @Override
                        public void run() {
                            mHandler.post(new Runnable(){
                                @Override
                                public void run() {
                                    // 알림창 띄우기
                                    pDialog.cancel();
                                    dialog.show();
                                    return;
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
        return resultLogin;
    }

    public void setpDialog(ProgressDialog pDialog){
        this.pDialog = pDialog;
        return;
    }


    public void setDialogActivity (Activity a){
        createDialog(a);
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
        dialog = builder.create();    // 알림창 객체 생성
        // 알림창 띄우기
    }

}
