package com.kiran.weighttracker;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kiran.weighttracker.database.MySQLiteOpenHelper;
import com.kiran.weighttracker.databinding.ActivityMainBinding;
import com.kiran.weighttracker.fragments.HomeFragment;
import com.kiran.weighttracker.fragments.TargetFragment;
import com.kiran.weighttracker.fragments.ProfileFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private ArrayList<ProfileModel> list = new ArrayList<>();
    private ProfileModel profileModel;
    private Session session;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
        profileModel = new ProfileModel();
        session = new Session(this);
        fragmentManager = getSupportFragmentManager();
        initView();
        loadFragment(new HomeFragment());
    }

    private void initView() {
        profileModel = mySQLiteOpenHelper.getUserDetails();
        binding.bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        binding.bottomNavigationView.setSelectedItemId(R.id.home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.target) {
            loadFragment(new TargetFragment());
        } else if (id == R.id.home) {
            loadFragment(new HomeFragment());
        } else if (id == R.id.profile) {
            loadFragment(new ProfileFragment());
        }
        return true;
    }

    private void loadFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }
}