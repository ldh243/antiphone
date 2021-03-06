package model;

import android.provider.ContactsContract;
import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import util.DateTimeUtils;

public class ProfileUserDAO {
    public Date[] get7DaysBefore(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -6);
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d5 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d6 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d7 = calendar.getTime();
        return new Date[]{d1, d2, d3,  d4, d5, d6, d7};
    }

    public String [] getDayOfWeek(Date date) {
        Date[] listDate = get7DaysBefore(date);
        String [] dayOfWeek = new String[7];
        for (int i = 0; i < 7; i++) {
            dayOfWeek[i] = listDate[i].toString().split( " ")[0];
        }
        return dayOfWeek;
    }

    public String getTotalTimeHolding(String userId) {
        String result;
        result = DateTimeUtils.generateDayHourMinutes(100000);
        return result;
    }
}
