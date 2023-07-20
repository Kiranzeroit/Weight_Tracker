package com.kiran.weighttracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kiran.weighttracker.MainActivity;
import com.kiran.weighttracker.Session;
import com.kiran.weighttracker.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    private boolean handler;
    private Session session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session= new Session(this);
        handler = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.getBooleanValue("LOGIN")){
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            }
        },2000);
    }
}
