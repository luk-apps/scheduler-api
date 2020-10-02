package com.wookie.lukapp.core.services;

import com.wookie.lukapp.api.DTO.TimeFrame;
import com.wookie.lukapp.core.util.TimeInterval;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.ArrayList;
import java.util.List;

public class TimeManagementService {

    /**
     * Returns difference in minutes between date time and a starting point. Time is represented as number
     * of minutes from starting point
     * @param dateTime
     * @param startingPoint
     * @return
     */
    public static int convertTimeToInt(DateTime startingPoint, DateTime dateTime) {
        return Minutes.minutesBetween(startingPoint, dateTime).getMinutes();
    }

    public static DateTime convertIntToTime(int time, DateTime startingPoint) {
        DateTime result = new DateTime(startingPoint);
        return result.plusMinutes(time);
    }

    public static boolean checkIfTimeIntervalMatchesListOfTimeIntervals(int start, int end, List<TimeInterval> timeIntervals) {
        for(TimeInterval t : timeIntervals) {
            if(start >= t.start && end <= t.end)
                return true;
        }
        return false;
    }

    public static int findUpperTimeLimit(List<TimeInterval> possibleTimeIntervals) {
        int upperTimeLimit;
        upperTimeLimit = possibleTimeIntervals.stream().mapToInt(v -> v.end).max().orElse(0);
        return upperTimeLimit;
    }

    /**
     * Converts list of time intervals represented as DateTime to list of intervals represented as integers.
     * To do that it needs starting point in time. All dates are convert relative to starting point witch is
     * represented as 0.
     * @param list
     * @param start
     * @return
     */
    public static ArrayList<TimeInterval> convertListOfTimeIntervalsToIntegers(List<TimeFrame> list, DateTime start) {
        ArrayList<TimeInterval> result = new ArrayList<>();
        for(TimeFrame p : list) {
            result.add(new TimeInterval(
                    TimeManagementService.convertTimeToInt(start, p.getStart()),
                    TimeManagementService.convertTimeToInt(start, p.getEnd())
            ));
        }
        return result;
    }
}
