package model;

import com.jjoe64.graphview.series.DataPoint;

import java.util.LinkedList;
import java.util.List;

import util.DateTimeUtils;

public class ProfileUserDAO {
    public List<DataPoint> chartHoldingFromDateToDate(String userId){
        List<DataPoint> list = new LinkedList<>();

        return list;
    }

    public String getTotalTimeHolding(String userId) {
        String result;
        result = DateTimeUtils.generateDayHourMinutes(100000);
        return result;
    }
}
