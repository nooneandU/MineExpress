package com.zc741.mineexpress;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private static final int READ_PHONE_STATE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //申请权限
        if (Build.VERSION.SDK_INT >= 23) {
            addReadPhoneState();
        } else {
            delayShow();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void addReadPhoneState() {
        int requestPhoneState = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        if (requestPhoneState != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_CODE);
            return;
        } else {
            delayShow();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_PHONE_STATE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                delayShow();
            } else {
                // Permission Denied
                finish();
            }
        }
    }

    //延迟显示
    private void delayShow() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_to_left);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 4000);
    }
}
