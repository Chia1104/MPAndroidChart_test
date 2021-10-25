package com.example.mpandroidchart_test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    BarChart mChart;
    PieChart pieChart;
    private static final String CHART_URL = "http://192.168.0.104:13306/GetData5.php";
    private static final String DCARD_URL = "http://192.168.0.104:13306/GetData4.php";
    List<Chart> chartList;
    private TextView mText;
    private Integer neg, neu, pos;
    private Integer posCount = 0, neuCount = 0, negCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chartList = new ArrayList<>();
        loadChartValue();
//        GroupBarChart();
        showPieChart();
    }

    public void GroupBarChart(){
        int DATA_COUNT = 3;
        mChart = findViewById(R.id.bar_chart);
        mChart.setDrawBarShadow(false);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(true);
        // empty labels so that the names are spread evenly
        String[] labels = {"", "1", "2", "3", ""};
        XAxis xAxis = mChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setAxisMinimum(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setTextSize(12);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularity(2);
        leftAxis.setLabelCount(8, true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);

        float[] valOne = {10};
        float[] valTwo = {60};
        float[] valThree = {50};

        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();
        ArrayList<BarEntry> barThree = new ArrayList<>();
        for (int i = 0; i < valOne.length; i++) {
            barOne.add(new BarEntry(i, valOne[i]));
            barTwo.add(new BarEntry(i, valTwo[i]));
            barThree.add(new BarEntry(i, valThree[i]));
        }

        BarDataSet set1 = new BarDataSet(barOne, "barOne");
        set1.setColor(Color.BLUE);
        BarDataSet set2 = new BarDataSet(barTwo, "barTwo");
        set2.setColor(Color.MAGENTA);
        BarDataSet set3 = new BarDataSet(barThree, "barTwo");
        set2.setColor(Color.GREEN);

        set1.setHighlightEnabled(false);
        set2.setHighlightEnabled(false);
        set3.setHighlightEnabled(false);
        set1.setDrawValues(false);
        set2.setDrawValues(false);
        set3.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        dataSets.add(set3);
        BarData data = new BarData(dataSets);
        float groupSpace = 0.4f;
        float barSpace = 0f;
        float barWidth = 0.3f;
        // (barSpace + barWidth) * 2 + groupSpace = 1
        data.setBarWidth(barWidth);
        // so that the entire chart is shown when scrolled from right to left
        xAxis.setAxisMaximum(labels.length - 1.1f);
        mChart.setData(data);
        mChart.setScaleEnabled(false);
        mChart.setVisibleXRangeMaximum(6f);
        mChart.groupBars(1f, groupSpace, barSpace);
        mChart.invalidate();

    }

    private void showPieChart(){
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
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public <T> Map<T, Long> countByForEachLoopWithGetOrDefault(List<T> inputList) {
        Map<T, Long> resultMap = new HashMap<>();
        inputList.forEach(e -> resultMap.put(e, resultMap.getOrDefault(e, 0L) + 1L));
        return resultMap;
    }

    private void loadChartValue(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, CHART_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
                mText = findViewById(R.id.textView);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        pos = 60;
        neu = 10;
        neg = 40;
        queue.add(jsonArrayRequest);
    }

}