package model;

import android.provider.ContactsContract;
import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import util.DateTimeUtils;

public class ProfileUserDAO {
    public Date[] get7DaysBefore() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);
        Date dateStartWeek = calendar.getTime();
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
        Date dateEndWeek = calendar.getTime();
        return new Date[]{dateStartWeek, d2, d3,  d4, d5, d6, dateEndWeek};
    }

    public DataPoint[] chartHoldingInWeek(String userId){
        Date[] listDate = get7DaysBefore();
        DataPoint [] list = new DataPoint[]{
                new DataPoint(listDate[0], 3),
                new DataPoint(listDate[1], 1),
                new DataPoint(listDate[2], 7),
                new DataPoint(listDate[3], 11),
                new DataPoint(listDate[4], 18),
                new DataPoint(listDate[5], 4),
                new DataPoint(listDate[6], 3)
        };
        return list;
    }

    public String [] getDayOfWeek() {
        Date[] listDate = get7DaysBefore();
        String [] dayOfWeek = new String[7];
        for (int i = 0; i < listDate.length; i++) {
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
