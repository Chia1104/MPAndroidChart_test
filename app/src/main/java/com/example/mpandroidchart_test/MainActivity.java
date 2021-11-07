package com.example.mpandroidchart_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    BarChart mChart;
    PieChart pieChart;
    private static final String DCARD_URL = "https://cguimfinalproject-test.herokuapp.com/GetData5.php";
    private static final String elementToFound_pos = "Positive";
    private static final String elementToFound_neu = "Neutral";
    private static final String elementToFound_neg = "Negative";
    List<Dcard> dcardList;
    List<String> chartValue;
    Integer neg, neu, pos;
    RecyclerView mRecyclerView;
    Adapter adapter;

    boolean success = false;
    MysqlCon con;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressBar progressBar;

    EditText edtxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.recyclerView);

        edtxt = findViewById(R.id.search_EdText);
        edtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        con = new MysqlCon();

        dcardList = new ArrayList<>();
        chartValue = new ArrayList<>();

//        SyncData orderData = new SyncData();
//        orderData.execute("");

        loadDcardWithVolley();

    }

    private void filter(String text) {
        ArrayList<Dcard> filteredList = new ArrayList<>();

        for (Dcard item : dcardList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            } else if (item.getContent().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    private void filter1(String text) {
        ArrayList<Dcard> filteredList1 = new ArrayList<>();

        for (Dcard item : dcardList) {
            if (item.getSaclassnum().toLowerCase().contains(text.toLowerCase())) {
                filteredList1.add(item);
            }
        }
        adapter.filterList1(filteredList1);
    }

    private class SyncData extends AsyncTask<String, String, String>{

        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn ERROR, See Android Monitor in the bottom for details!";
        ProgressDialog progress;
        List<String> chartList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(MainActivity.this, "Synchronising", "RecycleView Loading, Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Connection conn = con.CONN();
                if (conn == null){
                    success = false;
                } else {
                    String query = "SELECT dcard_rawdata.Id, dcard_rawdata.Title, dcard_rawdata.CreatedAt, dcard_rawdata.Content, nlp_analysis.SA_Score, nlp_analysis.SA_Class, comparison.Level, comparison.KeywordLevel1, comparison.KeywordLevel2, comparison.KeywordLevel3 FROM dcard_rawdata JOIN nlp_analysis ON dcard_rawdata.Id = nlp_analysis.Id JOIN comparison ON comparison.Id = nlp_analysis.Id WHERE dcard_rawdata.Id = nlp_analysis.Id ORDER BY  dcard_rawdata.Id DESC";
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    if (rs != null) {
                        while (rs.next()){
                            try {
                                Dcard dcard = new Dcard();
                                dcard.setSascore(rs.getString("SA_Score"));
                                dcard.setSaclass(rs.getString("SA_Class"));
                                dcard.setTitle(rs.getString("Title"));
                                dcard.setDate(rs.getString("CreatedAt"));
                                dcard.setContent(rs.getString("Content"));
                                dcard.setId(rs.getString("Id"));
                                dcard.setLv1(rs.getString("KeywordLevel1"));
                                dcard.setLv2(rs.getString("KeywordLevel2"));
                                dcard.setLv3(rs.getString("KeywordLevel3"));
                                dcardList.add(dcard);
                                chartList.add(rs.getString("SA_Class"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        msg = "FOUND";
                        success = true;
                    } else {
                        msg = "NO DATA FOUND!";
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        protected void onPostExecute(String msg) {
            progress.dismiss();
            Toast.makeText(MainActivity.this, "" + msg,Toast.LENGTH_LONG).show();
            if (success == false) {

            } else {
                try {
                    adapter = new Adapter(getApplicationContext(), dcardList);
                    mRecyclerView.setAdapter(adapter);

                    int posCount = Collections.frequency(chartList, elementToFound_pos);
                    int neuCount = Collections.frequency(chartList, elementToFound_neu);
                    int negCount = Collections.frequency(chartList, elementToFound_neg);

                    pos = posCount;
                    neu = neuCount;
                    neg = negCount;

                    showPieChart();
                } catch (Exception e) {

                }
            }
        }
    }

    public void loadDcardWithVolley(){
        HttpsTrustManager.allowAllSSL();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, DCARD_URL, null, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject dcardObject = response.getJSONObject(i);
                    Dcard dcard = new Dcard();
                    dcard.setSascore(dcardObject.getString("SA_Score"));
                    dcard.setSaclass(dcardObject.getString("SA_Class"));
                    dcard.setTitle(dcardObject.getString("Title"));
                    dcard.setDate(dcardObject.getString("CreatedAt"));
                    dcard.setContent(dcardObject.getString("Content"));
                    dcard.setId(dcardObject.getString("Id"));
                    dcard.setLv1(dcardObject.getString("KeywordLevel1"));
                    dcard.setLv2(dcardObject.getString("KeywordLevel2"));
                    dcard.setLv3(dcardObject.getString("KeywordLevel3"));
                    switch (dcardObject.getString("SA_Class")){
                        case "Positive":
                            dcard.setSaclassnum("2.0");
                            break;
                        case "Neutral":
                            dcard.setSaclassnum("0.0");
                            break;
                        case "Negative":
                            dcard.setSaclassnum("1.0");
                            break;
                    }
                    dcardList.add(dcard);
                    chartValue.add(dcardObject.getString("SA_Class"));
                }
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(), dcardList);
                mRecyclerView.setAdapter(adapter);
                int posCount = Collections.frequency(chartValue, elementToFound_pos);
                int neuCount = Collections.frequency(chartValue, elementToFound_neu);
                int negCount = Collections.frequency(chartValue, elementToFound_neg);

                pos = posCount;
                neu = neuCount;
                neg = negCount;

                showPieChart();
                progressBar.setVisibility(View.GONE);
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(MainActivity.this, error.getMessage(),Toast.LENGTH_LONG).show();
            error.printStackTrace();
        });
        queue.add(jsonArrayRequest);
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

//        pieChart.setDrawSliceText(false);
        pieChart.setData(pieData);
        pieChart.notifyDataSetChanged();
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                e.getData();
                String txt = String.valueOf(h.getX());
                filter1(txt);
            }

            @Override
            public void onNothingSelected() {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplicationContext(), dcardList);
                mRecyclerView.setAdapter(adapter);
            }
        });
    }

}