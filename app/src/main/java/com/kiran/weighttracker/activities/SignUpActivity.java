package com.kiran.weighttracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kiran.weighttracker.MainActivity;
import com.kiran.weighttracker.databinding.ActivitySignUpBinding;
import com.kiran.weighttracker.modals.CommonModal;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySignUpBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        initView();
    }

    private void initView() {
        binding.btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btnSignUp) {
            if (isValidation()) {
                CommonModal commonModal = new CommonModal();
                commonModal.name = binding.etName.getText().toString().trim();
                commonModal.age = binding.etAge.getText().toString().trim();
                commonModal.email = binding.etEmail.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();
                commonModal.mobile = binding.etMobile.getText().toString().trim();
                firebaseAuth.createUserWithEmailAndPassword(commonModal.email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Sign Up", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
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
            binding.etWeightTarget.setError("First enter your mobile");
            return false;
        }
        return true;
    }
}
