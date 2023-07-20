package com.kiran.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kiran.weighttracker.database.MySQLiteOpenHelper;
import com.kiran.weighttracker.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MySQLiteOpenHelper mySQLiteOpenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
        initView();
    }

    private void initView() {

    }
}