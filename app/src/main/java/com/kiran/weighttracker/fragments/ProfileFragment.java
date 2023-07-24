package com.kiran.weighttracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.kiran.weighttracker.ProfileModel;
import com.kiran.weighttracker.R;
import com.kiran.weighttracker.database.MySQLiteOpenHelper;

public class ProfileFragment extends Fragment {
    private AppCompatTextView tvUserName, tvAgeNo, tvEmail,tvMobileNo;
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private ProfileModel profileModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater().inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mySQLiteOpenHelper = new MySQLiteOpenHelper(requireActivity());
        profileModel= new ProfileModel();
        initView(view);
    }

    private void initView(View view) {
        tvUserName = view.findViewById(R.id.tvUserName);
        tvAgeNo= view.findViewById(R.id.tvAgeNo);
        tvEmail= view.findViewById(R.id.tvEmail);
        tvMobileNo= view.findViewById(R.id.tvMobileNo);

        profileModel = mySQLiteOpenHelper.getUserDetails();

        tvUserName.setText(profileModel.name);
        tvAgeNo.setText(profileModel.age);
        tvEmail.setText(profileModel.email);
        tvMobileNo.setText(profileModel.mobile);
    }

}
