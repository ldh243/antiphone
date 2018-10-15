package com.example.jun.antiphone;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {
    BarGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        double y, x;
        x = -1;

        GraphView graph = findViewById(R.id.graph1);
        graph.setTitleColor(Color.RED);
        graph.setBackgroundColor(Color.CYAN);
        graph.setTitle("DAY LA CHART");
        series = new BarGraphSeries<>();
        series.setSpacing(50);
        series.setColor(Color.RED);
        for (int i = 0; i < 5; i++) {
            x = x + 1;
            y = x * x;
            series.appendData(new DataPoint(x, y), true, 100);
        }
        graph.addSeries(series);
        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.BLUE;
            }
        });
    }
}
