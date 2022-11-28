package com.example.gabriel_sanchez_s5.ui.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gabriel_sanchez_s5.R;
import com.example.gabriel_sanchez_s5.databinding.FragmentDashboardBinding;
import com.example.gabriel_sanchez_s5.db.DBHandler;
import com.example.gabriel_sanchez_s5.model.PersonalModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private BarChart barChart;
    private DBHandler dbHandler;
    private ArrayList<PersonalModel> personalList;

    private String []chargeType = new String[] {"Personal de aseo", "Conserje"};
    private int []colors= new int[] {Color.BLUE , Color.RED};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHandler = new DBHandler(root.getContext());
        personalList = dbHandler.getAllPersonal();
        barChart = (BarChart)binding.fragmentVerticalbarchartChart;
        createCharts();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Chart getSameChart(Chart chart, String description, int textColor,
                               int background){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);
        return chart;
    }
    private void legend (Chart chart) {
        Legend legend= chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        ArrayList<LegendEntry> entries = new ArrayList<>();
        for (int i=0; i< chargeType.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = colors[i];
            entry.label = chargeType[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
    private ArrayList <BarEntry> getBarEntries(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i=0; i< chargeType.length; i++){
            int iter = i;
            long value = personalList.stream().filter(f -> f.getChargeType().equals(chargeType[iter])).count();
            entries.add(new BarEntry(i,value));
        }
        return entries;
    }

    private void axisX (XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(chargeType));
    }
    private void axisLeft (YAxis axis){
        axis.setEnabled(false);
    }
    private void axisRight (YAxis axis){
        axis.setEnabled(false);
    }
    public void createCharts(){
        barChart=(BarChart)getSameChart(barChart, "Serie", Color.RED,
                Color.CYAN);
        barChart.setDrawBarShadow(true);
        barChart.setData(getBarData());
        barChart.invalidate();
        barChart.setDrawGridBackground(true);
        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
    }
    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colors);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }
    private BarData getBarData () {
        BarDataSet barDataSet = (BarDataSet) getData(new
                BarDataSet(getBarEntries(), ""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }

}