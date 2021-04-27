package com.example.ecommerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.polar.series.Column;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.dataBase.AsyncTask.GetAllOrderWithCustomer;
import com.example.ecommerceapp.dataBase.AsyncTask.GetOrderForGraph;
import com.example.ecommerceapp.dataBase.RoomFactory;
import com.example.ecommerceapp.models.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class GraphActivity extends AppCompatActivity {

    AnyChartView chartView;
    HashMap<String, Integer> arr_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        arr_chart = new HashMap<>();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        int id = sharedPref.getInt(getString(R.string.saved_user_ID), 0);

        chartView = findViewById(R.id.any_chart_view);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String endDate = df.format(c);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date result = cal.getTime();
        String StartDate = df.format(result);

        String[] StDate = StartDate.split("-");
        String[] EnDate = endDate.split("-");


        try {
            List<Order> list = new GetAllOrderWithCustomer(RoomFactory.getNotesDatabase(this).getCustomerDAO()).execute(id).get();

            for (int i = 0; i < list.size(); i++) {
                String orderDate = list.get(i).getOrdDate();
                String[] parts = orderDate.split("-");
                int day = Integer.valueOf(parts[0]);
                int mouth = Integer.valueOf(parts[1]);
                int year = Integer.valueOf(parts[2]);

                if (year >= Integer.valueOf(StDate[2]) && year <= Integer.valueOf(EnDate[2])) {
                    if (mouth >= Integer.valueOf(StDate[1]) && mouth <= Integer.valueOf(EnDate[1])) {
                        if (day >= Integer.valueOf(StDate[0]) && day <= Integer.valueOf(EnDate[0])) {

                            if (arr_chart.containsKey(orderDate)) {
                                int v = arr_chart.get(orderDate) + 1;
                                arr_chart.replace(orderDate, v);
                            } else {
                                arr_chart.put(orderDate, 1);
                            }

                            //  Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SetUpPieChart();
    }

    private void SetUpPieChart() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : arr_chart.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            dataEntries.add(new ValueDataEntry(key, value));
        }
        pie.data(dataEntries);
        chartView.setChart(pie);
    }
}