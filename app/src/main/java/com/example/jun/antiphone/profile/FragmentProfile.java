package com.example.jun.antiphone.profile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jun.antiphone.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.ProfileUserDAO;

public class FragmentProfile extends Fragment implements View.OnClickListener {
    View view;
    GraphView graph;
    LineGraphSeries<DataPoint> series;
    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public FragmentProfile() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("PERSONALLOG", "onStart: ");
        setCurrentDate();
        String currentSelectedDate = currentDay + "/" + currentMonth + "/" + currentYear;
        setChangeDateListener();
        initChart();
        setOnClickListener();
        drawChart(currentSelectedDate);
        setTotalTimeHolding();
    }

    private void setOnClickListener() {
        ImageView btnChangeDate = getActivity().findViewById(R.id.iconChangeDate);
        btnChangeDate.setOnClickListener(this);
    }

    private void setCurrentDate() {
        Calendar cal = Calendar.getInstance();
        currentYear = cal.get(Calendar.YEAR);
        currentMonth = cal.get(Calendar.MONTH) + 1;
        currentDay = cal.get(Calendar.DAY_OF_MONTH);
    }

    private void initChart() {
        graph = getActivity().findViewById(R.id.graph);
        graph.getGridLabelRenderer().setGridColor(Color.GRAY);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
        graph.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.LEFT);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Time(m)");
        graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.BLACK);
        graph.getGridLabelRenderer().setPadding(40);
    }

    private void drawChart(String dateString) {
        ProfileUserDAO dao = new ProfileUserDAO();
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date[] listDay = dao.get7DaysBefore(date);

        String title = listDay[0].toString().split(" ")[2] + ". "
                + listDay[0].toString().split(" ")[1] + " - "
                + listDay[6].toString().split(" ")[2] + ". "
                + listDay[6].toString().split(" ")[1];

        graph.setTitle(title);
        graph.setTitleColor(Color.BLACK);
        DataPoint[] list = dao.chartHoldingInWeek("123");
        String[] dayOfWeek = dao.getDayOfWeek(date);
        series = new LineGraphSeries<>(list);
        graph.removeAllSeries();
        graph.addSeries(series);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(dayOfWeek);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph.getGridLabelRenderer().setHorizontalAxisTitleTextSize(11);
    }

    private void setTotalTimeHolding() {
        ProfileUserDAO dao = new ProfileUserDAO();
        String totalTime = dao.getTotalTimeHolding("");
        TextView txtTotalTime = getActivity().findViewById(R.id.txtTotalTime);
        txtTotalTime.setText(totalTime);
    }

    public void changeDate() {
        Log.d("PERSONALLOG", "changeDate: ");
        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                mDateSetListener,
                currentYear, (currentMonth - 1), currentDay);
        dialog.show();
    }

    public void setChangeDateListener() {
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

    @Override
    public void onClick(View v) {
        Log.d("PERSONALLOG", "onClick: " + v.getId());
        switch (v.getId()) {
            case R.id.iconChangeDate:
                changeDate();
                break;
        }
    }
}











