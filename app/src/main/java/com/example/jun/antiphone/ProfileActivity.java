package com.example.jun.antiphone;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

        graph.getGridLabelRenderer().setGridColor(Color.GRAY);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
        graph.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.LEFT);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Time");
        graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.RED);
        ProfileUserDAO dao = new ProfileUserDAO();
        DataPoint [] list = dao.chartHoldingInWeek("123");
        String [] dayOfWeek = dao.getDayOfWeek();
        Date[] listDate = dao.get7DaysBefore();
        series = new LineGraphSeries<>(list);
        graph.addSeries(series);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);

        staticLabelsFormatter.setHorizontalLabels(new String[]{dayOfWeek[0], dayOfWeek[1], dayOfWeek[2], dayOfWeek[3], dayOfWeek[4], dayOfWeek[5], dayOfWeek[6]});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getViewport().setMaxX(listDate[6].getTime()+10000000);
        graph.getViewport().setMinX(listDate[0].getTime()-10000000);
        graph.getViewport().setXAxisBoundsManual(true);
        setTotalTimeHolding();
    }
    private void setTotalTimeHolding() {
        Intent intent = this.getIntent();
        Bundle userInfo = intent.getBundleExtra("USERINFO");
        Log.d("Tagggggg", "setTotalTimeHolding: " + userInfo);

        String userId = (String) userInfo.get("userId");
        ProfileUserDAO dao = new ProfileUserDAO();
        String totalTime = dao.getTotalTimeHolding(userId);
        TextView txtTotalTime = findViewById(R.id.txtTotalTime);
        txtTotalTime.setText(totalTime);


//        Log.d("Tagggggg", dateStartWeek + " " + dateEndWeek);
    }
}
