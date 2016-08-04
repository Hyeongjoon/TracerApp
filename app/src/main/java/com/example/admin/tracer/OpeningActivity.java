package com.example.admin.tracer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



/**
 * Created by admin on 2016-07-15.
 */
public class OpeningActivity extends AppCompatActivity {
    public static Activity OpeningActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        OpeningActivity = this;
        super.onCreate(savedInstanceState);
        boolean isRunIntro = getIntent().getBooleanExtra("intro", true);
        if(isRunIntro) {
            beforeIntro();
        } else {
            afterIntro(savedInstanceState);
        }
    }



    private void beforeIntro() {
        // 약 2초간 인트로 화면을 출력.
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(OpeningActivity.this, OpeningActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("intro", false);
                startActivity(intent);
                // 액티비티 이동시 페이드인/아웃 효과를 보여준다. 즉, 인트로
                //    화면에 부드럽게 사라진다.
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        }, 2000);
    }

    // 인트로 화면 이후.
    private void afterIntro(Bundle savedInstanceState) {
        // 기본 테마를 지정한다.
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_opening);
    }

    public void opening_onClick(View v){
        switch (v.getId()) {
            case R.id.opening_login: {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        LoginActivity.class);
                startActivity(intent);
                return;
            }
            case R.id.opening_signUp: {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        SignUpActivity.class);
                startActivity(intent);
                return;
            }
        }
    }
}
