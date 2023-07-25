package com.kiran.weighttracker.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kiran.weighttracker.R;
import com.kiran.weighttracker.adapters.TargetAdapter;
import com.kiran.weighttracker.database.MySQLiteOpenHelper;
import com.kiran.weighttracker.interfaces.ClickListenerInterface;
import com.kiran.weighttracker.modals.TargetModal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TargetFragment extends Fragment implements View.OnClickListener, ClickListenerInterface {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private TargetAdapter targetAdapter;
    private AppCompatEditText etTime, etDate, etWeight, etCalories;
    private AppCompatButton btnSubmit;
    private List<TargetModal> targetList = new ArrayList<>();
    private MySQLiteOpenHelper mySQLiteOpenHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater().inflate(R.layout.fragment_target, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mySQLiteOpenHelper = new MySQLiteOpenHelper(requireActivity());
        initView(view);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);
        targetList = mySQLiteOpenHelper.getUsersTargetList();


        targetAdapter = new TargetAdapter(requireActivity(), targetList, this);
        recyclerView.setAdapter(targetAdapter);
        targetAdapter.notifyDataSetChanged();

        try {
            targetList = mySQLiteOpenHelper.getUsersTargetList();
            if (targetList.size() != 0)
                targetAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        if (view == floatingActionButton) {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View customLayout = getLayoutInflater().inflate(R.layout.alertdialog_edit_text, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        dialog.show();
        etTime = customLayout.findViewById(R.id.etTime);
        etDate = customLayout.findViewById(R.id.etDate);
        etWeight = customLayout.findViewById(R.id.etWeight);
        etCalories = customLayout.findViewById(R.id.etCalories);
        btnSubmit = customLayout.findViewById(R.id.btnSubmit);
        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker();
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidation()) {
                    String time = etTime.getText().toString().trim();
                    String date = etDate.getText().toString().trim();
                    String weight = etWeight.getText().toString().trim();
                    String calories = etCalories.getText().toString().trim();

                    mySQLiteOpenHelper.saveTargetDetails(""+time,""+ date, ""+weight,""+ calories);

                    try {
                        targetList = mySQLiteOpenHelper.getUsersTargetList();
                        if (targetList.size() != 0)
                            targetAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }
        });


    }

    private boolean isValidation() {
        if (etTime.getText().toString().trim().isEmpty()) {
            etTime.requestFocus();
            etTime.setError("select time first");
            return false;
        } else if (etDate.getText().toString().trim().isEmpty()) {
            etDate.requestFocus();
            etDate.setError("select date first");
            return false;
        } else if (etWeight.getText().toString().trim().isEmpty()) {
            etWeight.requestFocus();
            etWeight.setError("enter your weight");
            return false;
        } else if (etCalories.getText().toString().trim().isEmpty()) {
            etCalories.requestFocus();
            etCalories.setError("enter your calories");
            return false;
        }
        return true;
    }

    public void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "-" + (month + 1) + "-" + year;
                etDate.setText(date);
            }
        }, day, month, year);
        datePickerDialog.show();
    }

    public void openTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                etTime.setText(hourOfDay + ":" + minute);
            }
        }, hour, minute, false);

        timePickerDialog.show();
    }

    @Override
    public void onClick(TargetModal targetModal, int position) {

    }
}
