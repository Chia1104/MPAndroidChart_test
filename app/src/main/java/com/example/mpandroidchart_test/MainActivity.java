package com.example.mpandroidchart_test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    BarChart mChart;
    PieChart pieChart;
    private static final String CHART_URL = "http://192.168.0.104:13306/GetData5.php";
    private static final String DCARD_URL = "http://192.168.0.104:13306/GetData4.php";
    private static final String elementToFound_pos = "Positive";
    private static final String elementToFound_neu = "Neutral";
    private static final String elementToFound_neg = "Negative";
    private List<Chart> chartList;
    private TextView mText;
    private Integer neg, neu, pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chartList = new ArrayList<>();
        mText = findViewById(R.id.textView);
        loadChartValue();
        countClass();
//        GroupBarChart();
        showPieChart();
    }

//    public void GroupBarChart(){
//        int DATA_COUNT = 3;
//        mChart = findViewById(R.id.bar_chart);
//        mChart.setDrawBarShadow(false);
//        mChart.getDescription().setEnabled(false);
//        mChart.setPinchZoom(false);
//        mChart.setDrawGridBackground(true);
//        // empty labels so that the names are spread evenly
//        String[] labels = {"", "1", "2", "3", ""};
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(true);
//        xAxis.setGranularity(1f); // only intervals of 1 day
//        xAxis.setTextColor(Color.BLACK);
//        xAxis.setTextSize(12);
//        xAxis.setAxisLineColor(Color.WHITE);
//        xAxis.setAxisMinimum(1f);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
//
//        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setTextColor(Color.BLACK);
//        leftAxis.setTextSize(12);
//        leftAxis.setAxisLineColor(Color.WHITE);
//        leftAxis.setDrawGridLines(true);
//        leftAxis.setGranularity(2);
//        leftAxis.setLabelCount(8, true);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//
//        mChart.getAxisRight().setEnabled(false);
//        mChart.getLegend().setEnabled(false);
//
//        float[] valOne = {10};
//        float[] valTwo = {60};
//        float[] valThree = {50};
//
//        ArrayList<BarEntry> barOne = new ArrayList<>();
//        ArrayList<BarEntry> barTwo = new ArrayList<>();
//        ArrayList<BarEntry> barThree = new ArrayList<>();
//        for (int i = 0; i < valOne.length; i++) {
//            barOne.add(new BarEntry(i, valOne[i]));
//            barTwo.add(new BarEntry(i, valTwo[i]));
//            barThree.add(new BarEntry(i, valThree[i]));
//        }
//
//        BarDataSet set1 = new BarDataSet(barOne, "barOne");
//        set1.setColor(Color.BLUE);
//        BarDataSet set2 = new BarDataSet(barTwo, "barTwo");
//        set2.setColor(Color.MAGENTA);
//        BarDataSet set3 = new BarDataSet(barThree, "barTwo");
//        set2.setColor(Color.GREEN);
//
//        set1.setHighlightEnabled(false);
//        set2.setHighlightEnabled(false);
//        set3.setHighlightEnabled(false);
//        set1.setDrawValues(false);
//        set2.setDrawValues(false);
//        set3.setDrawValues(false);
//
//        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//        dataSets.add(set1);
//        dataSets.add(set2);
//        dataSets.add(set3);
//        BarData data = new BarData(dataSets);
//        float groupSpace = 0.4f;
//        float barSpace = 0f;
//        float barWidth = 0.3f;
//        // (barSpace + barWidth) * 2 + groupSpace = 1
//        data.setBarWidth(barWidth);
//        // so that the entire chart is shown when scrolled from right to left
//        xAxis.setAxisMaximum(labels.length - 1.1f);
//        mChart.setData(data);
//        mChart.setScaleEnabled(false);
//        mChart.setVisibleXRangeMaximum(6f);
//        mChart.groupBars(1f, groupSpace, barSpace);
//        mChart.invalidate();
//
//    }

    public void showPieChart(){
        pieChart = findViewById(R.id.pieChart_view);
        pieChart.getDescription().setEnabled(false);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        typeAmountMap.put("Positive",pos);
        typeAmountMap.put("Neutral",neu);
        typeAmountMap.put("Negative",neg);

        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FFDD55"));
        colors.add(Color.parseColor("#FFA488"));
        colors.add(Color.parseColor("#33FFAA"));

        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(Objects.requireNonNull(typeAmountMap.get(type)).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @SuppressLint("SetTextI18n")
    public void loadChartValue(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, CHART_URL, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject chartObject = response.getJSONObject(i);
                    Chart chart = new Chart();
                    chart.setSaScore(chartObject.getString("SA_Score"));
                    chart.setSaClass(chartObject.getString("SA_Class"));
                    chartList.add(chart);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            refreshData();
        }, error -> {

        });
        queue.add(jsonArrayRequest);

        pos = 10;
        neu = 10;
        neg = 10;
    }

    public void refreshData() {

        if (chartList.isEmpty()) {
            pieChart.setVisibility(android.view.View.VISIBLE);
        } else {
            pieChart.setVisibility(android.view.View.GONE);
        }
    }

    public void countClass() {

        //test
//        List<String> testList = Arrays.asList(
//                "Chennai","Bangalore","Pune","Hyderabad",
//                "Chennai","Pune","Mysore","Delhi","Hyderabad",
//                "Pune"
//        );
//        String testElementToFound = "Chennai";
//        int frequency = Collections.frequency(testList, testElementToFound);
//        mText.setText("frequency: " + frequency);

        //using Collections
        int posCount = Collections.frequency(chartList, elementToFound_pos);
        int neuCount = Collections.frequency(chartList, elementToFound_neu);
        int negCount = Collections.frequency(chartList, elementToFound_neg);
        pos = posCount;
        neu = neuCount;
        neg = negCount;

        //using Map
//        HashMap<Chart, Integer> map = new HashMap<>();
//        for(Chart s : chartList)
//        {
//            map.put(s, map.get(s)!=null ? map.get(s)+1 : 1);
//        }
//        try{
//            int posCount = map.get(elementToFound_pos);
//            int neuCount = map.get(elementToFound_neu);
//            int negCount = map.get(elementToFound_neg);
//            mText.setText(posCount + neuCount + negCount);
//        } catch (Exception e){
//            mText.setText(e + "");
//        }


    }

}