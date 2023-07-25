package com.kiran.weighttracker.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.kiran.weighttracker.R;
import com.kiran.weighttracker.database.MySQLiteOpenHelper;
import com.kiran.weighttracker.modals.TargetModal;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private BarChart barChart;
    private BarDataSet barDataSet1, barDataSet2;
    private ArrayList<BarEntry> barEntries;
    private List<TargetModal> targetList = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater().inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mySQLiteOpenHelper = new MySQLiteOpenHelper(requireActivity());
        initView(view);

    }

    private void initView(View view) {
        barChart = view.findViewById(R.id.barChart);
        targetList = mySQLiteOpenHelper.getUsersTargetList();

        barDataSet1 = new BarDataSet(getBarEntriesOne(), "Weight");
        barDataSet1.setColor(requireActivity().getResources().getColor(R.color.app));
        barDataSet2 = new BarDataSet(getBarEntriesTwo(), "Calories");
        barDataSet2.setColor(Color.BLUE);

        BarData data = new BarData(barDataSet1, barDataSet2);

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getListAxis()));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);
        float barSpace = 0.1f;
        float groupSpace = 0.5f;
        data.setBarWidth(0.15f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.animate();
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();
    }

    private ArrayList<BarEntry> getBarEntriesOne() {
        barEntries = new ArrayList<>();
        for (int i = 0; i < targetList.size(); i++) {
            barEntries.add(new BarEntry(i, Float.parseFloat(targetList.get(i).weight)));
        }
       /* for (TargetModal targetModal : targetList) {
            barEntries.add(new BarEntry(10f, Float.parseFloat(targetModal.weight)));
        }*/
        return barEntries;
    }

    private ArrayList<BarEntry> getBarEntriesTwo() {
        barEntries = new ArrayList<>();
        for (int i = 0; i < targetList.size(); i++) {
            barEntries.add(new BarEntry(i, Float.parseFloat(targetList.get(i).Calories)));
        }
        /*for (TargetModal targetModal : targetList) {
            barEntries.add(new BarEntry(10f, Float.parseFloat(targetModal.Calories)));
        }*/
        return barEntries;
    }

    protected String[] getList() {
        String[] listxAxis = new String[targetList.size()];
        for (int i = 0; i < targetList.size(); i++) {
            listxAxis[i] = targetList.get(i).date;
        }
        return listxAxis;
    }

    protected ArrayList<String> getListAxis() {
        list.clear();
        for (TargetModal item : targetList) {
            list.add(item.date);
        }
        return list;
    }

}
