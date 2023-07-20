package com.kiran.weighttracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kiran.weighttracker.DatabaseHelper;
import com.kiran.weighttracker.MainActivity;
import com.kiran.weighttracker.Session;
import com.kiran.weighttracker.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySignUpBinding binding;
    private Session session;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new Session(this);
        databaseHelper = new DatabaseHelper(this);
        initView();
    }

    private void initView() {
        binding.btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btnSignUp) {
            if (isValidation()) {
                String name = binding.etName.getText().toString().trim();
                String age = binding.etAge.getText().toString().trim();
                String email = binding.etEmail.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();
                String mobile = binding.etMobile.getText().toString().trim();
                String targetWeight = binding.etWeightTarget.getText().toString().trim();

                Boolean checkUserEmail = databaseHelper.checkEmail(email);
                if (checkUserEmail) {
                    Boolean insert = databaseHelper.insertData(name, age, email, password, mobile, targetWeight);
                    if (insert) {
                        Toast.makeText(this, "SignUp successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "SignUp failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "User already exist, please login", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean isValidation() {
        if (binding.etName.getText().toString().trim().isEmpty()) {
            binding.etName.requestFocus();
            binding.etName.setError("First enter your name");
            return false;
        } else if (binding.etAge.getText().toString().trim().isEmpty()) {
            binding.etAge.requestFocus();
            binding.etAge.setError("First enter your age");
            return false;
        } else if (binding.etEmail.getText().toString().trim().isEmpty()) {
            binding.etEmail.requestFocus();
            binding.etEmail.setError("First enter your email");
            return false;
        } else if (binding.etPassword.getText().toString().trim().isEmpty()) {
            binding.etPassword.requestFocus();
            binding.etPassword.setError("First enter your password");
            return false;
        } else if (binding.etMobile.getText().toString().trim().isEmpty()) {
            binding.etMobile.requestFocus();
            binding.etMobile.setError("First enter your mobile");
            return false;
        } else if (binding.etWeightTarget.getText().toString().trim().isEmpty()) {
            binding.etWeightTarget.requestFocus();
            binding.etWeightTarget.setError("First enter your target weight");
            return false;
        }
        return true;
    }
}
