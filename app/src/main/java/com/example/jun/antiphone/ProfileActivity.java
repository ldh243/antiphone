package com.example.jun.antiphone;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import model.ProfileUserDAO;
import util.DateTimeUtils;

public class ProfileActivity extends AppCompatActivity {
    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        double y, x;
        x = 0;

        GraphView graph = findViewById(R.id.graph1);
//        graph.setTitleColor(Color.BLACK);
//        graph.setBackgroundColor(Color.CYAN);
//        graph.setTitle("DAY LA CHART");
//        graph.getGridLabelRenderer().setGridColor(Color.GRAY);
//        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
//        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);

        // generate Dates
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();



    // you can directly pass Date objects to DataPoint-Constructor
    // this will convert the Date to double via Date#getTime()
        series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(d1, 1),
                new DataPoint(d2, 5),
                new DataPoint(d3, 3)
        });

        graph.addSeries(series);

// set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

// set manual x bounds   to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        // styling
//        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
//            @Override
//            public int get(DataPoint data) {
//                return Color.BLUE;
//            }
//        });
    }
    private void setTotalTimeHolding() {
        Intent intent = this.getIntent();
        Bundle userInfo = intent.getBundleExtra("USERINFO");
        String userId = (String) userInfo.get("userId");
        ProfileUserDAO dao = new ProfileUserDAO();
        String totalTime = dao.getTotalTimeHolding(userId);

    }
}
