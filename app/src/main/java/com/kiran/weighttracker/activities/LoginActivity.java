package com.kiran.weighttracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kiran.weighttracker.MainActivity;
import com.kiran.weighttracker.Session;
import com.kiran.weighttracker.database.MySQLiteOpenHelper;
import com.kiran.weighttracker.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    private Session session;
    private MySQLiteOpenHelper mySQLiteOpenHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
        session = new Session(this);
        initView();

    }

    private void initView() {
        binding.btnSignIn.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btnSignIn) {
            if (isValidation()) {
                String email = binding.etEmail.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();

                boolean isUserExist = mySQLiteOpenHelper.isUserExist(email);
                if (isUserExist) {
                    session.setBooleanValue("LOGIN", true);
                    session.setStringValue("email", email);
                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "User doesn't exist, please signup", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (view == binding.btnSignUp) {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }

    }

    private boolean isValidation() {
        if (binding.etEmail.getText().toString().trim().isEmpty()) {
            binding.etEmail.requestFocus();
            binding.etEmail.setError("First enter your email");
            return false;
        } else if (binding.etPassword.getText().toString().trim().isEmpty()) {
            binding.etPassword.requestFocus();
            binding.etPassword.setError("First enter your password");
            return false;
        }
        return true;
    }
}
