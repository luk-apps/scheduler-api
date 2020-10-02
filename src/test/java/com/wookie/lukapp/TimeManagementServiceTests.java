package com.wookie.lukapp;

import com.wookie.lukapp.api.DTO.TimeFrame;
import com.wookie.lukapp.core.util.TimeInterval;
import com.wookie.lukapp.core.services.TimeManagementService;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest
public class TimeManagementServiceTests {

    @Test
    void convertTimeToIntTest() {
        DateTime start = new DateTime().withTime(9,0,0,0);
        DateTime pointInTime = new DateTime().withTime(10, 0, 0, 0);
        assertEquals(TimeManagementService.convertTimeToInt(start, pointInTime), 60);
    }

    @Test
    void convertTimeToIntTest2() {
        DateTime start = new DateTime().withTime(9,0,0,0);
        DateTime pointInTime = new DateTime().withTime(10, 0, 0, 0);
        assertNotEquals(TimeManagementService.convertTimeToInt(start, pointInTime), 50);
    }

    @Test
    void convertTimeToIntTest3() {
        DateTime start = new DateTime().withTime(9,0,0,0);
        DateTime pointInTime = new DateTime().withTime(10, 15, 0, 0);
        assertEquals(TimeManagementService.convertTimeToInt(start, pointInTime), 75);
    }

    @Test
    void convertTimeToIntTest4() {
        DateTime start = new DateTime().withTime(9,0,0,0);
        DateTime pointInTime = new DateTime().withTime(8, 15, 0, 0);
        assertEquals(TimeManagementService.convertTimeToInt(start, pointInTime), -45);
    }

    @Test
    void findUpperTimeLimitTest() {
        ArrayList<TimeInterval> timeIntervals = new ArrayList<>();
        timeIntervals.add(new TimeInterval(4, 5));
        timeIntervals.add(new TimeInterval(15, 100));
        timeIntervals.add(new TimeInterval(1, 15));
        assertEquals(100, TimeManagementService.findUpperTimeLimit(timeIntervals));
        assertNotEquals(5, TimeManagementService.findUpperTimeLimit(timeIntervals));
    }

    @Test
    void findUpperTimeLimitEmptyListTest() {
        ArrayList<TimeInterval> timeIntervals = new ArrayList<>();
        assertEquals(0, TimeManagementService.findUpperTimeLimit(timeIntervals));
    }

    @Test
    void checkIfTimeIntervalMatchesListOfTimeIntervalsTest() {
        List<TimeInterval> timeIntervals = new ArrayList<>();
        timeIntervals.add(new TimeInterval(0, 50));
        timeIntervals.add(new TimeInterval(100, 150));
        timeIntervals.add(new TimeInterval(60, 70));
        boolean result = TimeManagementService.checkIfTimeIntervalMatchesListOfTimeIntervals(0,25, timeIntervals);
        assertEquals(true, result);
        boolean result2 = TimeManagementService.checkIfTimeIntervalMatchesListOfTimeIntervals(0,51, timeIntervals);
        assertEquals(false, result2);
        boolean result3 = TimeManagementService.checkIfTimeIntervalMatchesListOfTimeIntervals(0,70, timeIntervals);
        assertEquals(false, result3);
    }

    @Test
    void convertListOfTimeIntervalsToIntegersTest() {
        List<TimeFrame> list = new ArrayList<>();
        List<TimeInterval> result;
        DateTime startintPoint = new DateTime().withTime(9,0,0,0);
        list.add(new TimeFrame(new DateTime().withTime(9,0,0,0), new DateTime().withTime(10,0,0,0)));
        list.add(new TimeFrame(new DateTime().withTime(11,0,0,0), new DateTime().withTime(12,0,0,0)));
        result = TimeManagementService.convertListOfTimeIntervalsToIntegers(list, startintPoint);

        assertEquals(2, result.size());
        assertEquals(new TimeInterval(0, 60), result.get(0));
        assertEquals(new TimeInterval(120,180), result.get(1));
    }

    @Test
    void convertIntToTimeTest() {
        int time = 62;
        DateTime start = new DateTime().withTime(9,0,0,0);
        DateTime result = TimeManagementService.convertIntToTime(time, start);
        assertEquals(new DateTime().withTime(10,2,0,0), result);
    }
}
