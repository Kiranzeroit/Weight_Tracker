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
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private BarChart barChart;
    private BarDataSet barDataSet1, barDataSet2;
    private ArrayList<BarEntry> barEntries;
    private List<TargetModal> targetList = new ArrayList<>();

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
        xAxis.setValueFormatter(new IndexAxisValueFormatter(Collections.singleton("jj")));
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
        for (TargetModal targetModal : targetList) {
            barEntries.add(new BarEntry(10f, Float.parseFloat(targetModal.weight)));
        }
        return barEntries;
    }

    private ArrayList<BarEntry> getBarEntriesTwo() {
        barEntries = new ArrayList<>();
        for (TargetModal targetModal : targetList) {
            barEntries.add(new BarEntry(10f, Float.parseFloat(targetModal.Calories)));
        }
        return barEntries;
    }

 /*   private List<IBarDataSet> getDataSet() {
        List<IBarDataSet> dataSets = null;

        List<BarEntry> listSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(8f,Float.parseFloat(targetModal.Calories));
        listSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(8f, 70f);
        listSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(8f, 68f);
        listSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(8f, 60);
        listSet1.add(v1e4);



        List<BarEntry> listSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(8f,Float.parseFloat(targetModal.weight));
        listSet2.add(v2e1);
        BarEntry v2e2 = new BarEntry(8f,50f);
        listSet2.add(v2e2);
        BarEntry v2e3 = new BarEntry(8f, 49f);
        listSet2.add(v2e3);
        BarEntry v2e4 = new BarEntry(8f, 55f);
        listSet2.add(v2e4);

        BarDataSet barDataSet1 = new BarDataSet(listSet1, "Current Weight");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(listSet2, "Calories Burn");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }*/

}
