package com.example.jun.antiphone;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import model.ProfileUserDAO;
import util.DateTimeUtils;

public class ProfileActivity extends AppCompatActivity {
    LineGraphSeries<DataPoint> series;
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Calendar cal = Calendar.getInstance();
        currentYear = cal.get(Calendar.YEAR);
        currentMonth = cal.get(Calendar.MONTH);
        currentDay = cal.get(Calendar.DAY_OF_MONTH);
        currentMonth = currentMonth + 1;
        String currentSelectedDate = currentDay + "/" +  currentMonth + "/" + currentYear;

        drawChart(currentSelectedDate);
        setTotalTimeHolding();    }
    private void drawChart(String dateString) {
        GraphView graph = findViewById(R.id.graph1);
        graph.getGridLabelRenderer().setGridColor(Color.GRAY);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
        graph.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.LEFT);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Time(m)");
        graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.BLACK);
        graph.getGridLabelRenderer().setPadding(40);
        ProfileUserDAO dao = new ProfileUserDAO();

        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (Exception e){
            e.printStackTrace();
        }
        Date[] listDay = dao.get7DaysBefore(date);

        String title = listDay[0].toString().split(" ")[2] + ". "
                + listDay[0].toString().split(" ")[1] + " - "
                + listDay[6].toString().split(" ")[2] + ". "
                + listDay[6].toString().split(" ")[1];

        graph.setTitle(title);
        graph.setTitleColor(Color.BLACK);
        DataPoint [] list = dao.chartHoldingInWeek("123");
        String [] dayOfWeek = dao.getDayOfWeek(date);
        series = new LineGraphSeries<>(list);
        graph.addSeries(series);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(dayOfWeek);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph.getGridLabelRenderer().setHorizontalAxisTitleTextSize(11);
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
    }

    public void changeDate(View view) {

        DatePickerDialog dialog = new DatePickerDialog(
                ProfileActivity.this,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                mDateSetListener,
                currentYear, currentMonth - 1, currentDay);
        dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" +  month + "/" + year;
                currentYear = year;
                currentMonth = month;
                currentDay = day;
                drawChart(date);
            }
        };
    }
}
