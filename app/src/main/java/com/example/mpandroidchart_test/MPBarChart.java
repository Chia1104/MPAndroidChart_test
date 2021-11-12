package com.example.mpandroidchart_test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MPBarChart extends AppCompatActivity {

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data set.
    BarDataSet barDataSet1, barDataSet2;

    // array list for storing entries.
    ArrayList barEntries;

    // creating a string array for displaying days.
    String[] days = new String[]{"Sunday", "Monday", "Tuesday", "Thursday", "Friday", "Saturday"};

    List<Integer> monthValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpbar_chart);

        monthValue = new ArrayList<>();

        for (int i = 0; i >= -5; i--){
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, i);
            int month = c.get(Calendar.MONTH) + 1;
            monthValue.add(month);
        }
        Arrays.sort(new List[]{monthValue});
        String[] a=Arrays.toString(new List[]{monthValue}).split("[\\[\\]]")[1].split(", ");
        Toast.makeText(getApplicationContext(),
                "Month: " + monthValue + a, Toast.LENGTH_SHORT).show();

        barChart = findViewById(R.id.bar_chart);

        // creating a new bar data set.
        barDataSet1 = new BarDataSet(getBarEntriesOne(), "First Set");
        barDataSet1.setColor(getApplicationContext().getResources().getColor(R.color.purple_200));
        barDataSet2 = new BarDataSet(getBarEntriesTwo(), "Second Set");
        barDataSet2.setColor(Color.BLUE);

        // below line is to add bar data set to our bar data.
        BarData data = new BarData(barDataSet1, barDataSet2);

        // after adding data to our bar data we
        // are setting that data to our bar chart.
        barChart.setData(data);

        // below line is to remove description
        // label of our bar chart.
        barChart.getDescription().setEnabled(false);

        // below line is to get x axis
        // of our bar chart.
        XAxis xAxis = barChart.getXAxis();

        // below line is to set value formatter to our x-axis and
        // we are adding our days to our x axis.
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));

        // below line is to set center axis
        // labels to our bar chart.
        xAxis.setCenterAxisLabels(true);

        // below line is to set position
        // to our x-axis to bottom.
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // below line is to set granularity
        // to our x axis labels.
        xAxis.setGranularity(1);

        // below line is to enable
        // granularity to our x axis.
        xAxis.setGranularityEnabled(true);

        // below line is to make our
        // bar chart as draggable.
        barChart.setDragEnabled(true);

        // below line is to make visible
        // range for our bar chart.
        barChart.setVisibleXRangeMaximum(3);

        // below line is to add bar
        // space to our chart.
        float barSpace = 0.1f;

        // below line is use to add group
        // spacing to our bar chart.
        float groupSpace = 0.5f;

        // we are setting width of
        // bar in below line.
        data.setBarWidth(0.15f);

        // below line is to set minimum
        // axis to our chart.
        barChart.getXAxis().setAxisMinimum(0);

        // below line is to
        // animate our chart.
        barChart.animate();

        // below line is to group bars
        // and add spacing to it.
        barChart.groupBars(0, groupSpace, barSpace);

        // below line is to invalidate
        // our bar chart.
        barChart.invalidate();
    }

    // array list for first set
    private ArrayList<BarEntry> getBarEntriesOne() {

        // creating a new array list
        barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, 4));
        barEntries.add(new BarEntry(2f, 6));
        barEntries.add(new BarEntry(3f, 8));
        barEntries.add(new BarEntry(4f, 2));
        barEntries.add(new BarEntry(5f, 4));
        barEntries.add(new BarEntry(6f, 1));
        return barEntries;
    }

    // array list for second set.
    private ArrayList<BarEntry> getBarEntriesTwo() {

        // creating a new array list
        barEntries = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(new BarEntry(1f, 8));
        barEntries.add(new BarEntry(2f, 12));
        barEntries.add(new BarEntry(3f, 4));
        barEntries.add(new BarEntry(4f, 1));
        barEntries.add(new BarEntry(5f, 7));
        barEntries.add(new BarEntry(6f, 3));
        return barEntries;
    }
}