package com.wookie.lukapp;

import com.wookie.lukapp.core.algorithm.possibleOccurences.PossibleOccurencesService;
import com.wookie.lukapp.core.util.TimeInterval;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PossibleOccurencesServiceTest {

    @Autowired
    PossibleOccurencesService possibleOccurencesService;

    @Test
    void findPossibleOccurencesTest() {
        List<TimeInterval> possibleTimeIntervals = new ArrayList<>();
        List<TimeInterval> eventPossibleTimeIntervals = new ArrayList<>();
        List<TimeInterval> result;

        // time intervals that are possible in general
        possibleTimeIntervals.add(new TimeInterval(0,50));
        possibleTimeIntervals.add(new TimeInterval(75, 125));
        possibleTimeIntervals.add(new TimeInterval(150,200));

        // time intervals that are possible for specific event
        eventPossibleTimeIntervals.add(new TimeInterval(0, 200));

        result = possibleOccurencesService.findPossibleOccurences(possibleTimeIntervals, eventPossibleTimeIntervals, 50, 10);
        assertEquals(3, result.size());
        assertEquals(0, result.get(0).start);
        assertEquals(50, result.get(0).end);

        assertEquals(75, result.get(1).start);
        assertEquals(125, result.get(1).end);

        assertEquals(150, result.get(2).start);
        assertEquals(200, result.get(2).end);
    }

    @Test
    void findPossibleTimeIntervalsWithLists2() {
        List<TimeInterval> possibleTimeIntervals = new ArrayList<>();
        List<TimeInterval> eventPossibleTimeIntervals = new ArrayList<>();
        List<TimeInterval> result;

        // time intervals that are possible in general
        possibleTimeIntervals.add(new TimeInterval(0,50));
        possibleTimeIntervals.add(new TimeInterval(60, 100));
        possibleTimeIntervals.add(new TimeInterval(120, 150));

        // time intervals that are possible for specific event
        eventPossibleTimeIntervals.add(new TimeInterval(0, 40));
        eventPossibleTimeIntervals.add(new TimeInterval(50, 80));
        eventPossibleTimeIntervals.add(new TimeInterval(125, 150));

        result = possibleOccurencesService.findPossibleOccurences(possibleTimeIntervals, eventPossibleTimeIntervals, 20, 10);
        assertEquals(5, result.size());
        assertEquals(0, result.get(0).start);
        assertEquals(20, result.get(0).end);

        assertEquals(10, result.get(1).start);
        assertEquals(30, result.get(1).end);

        assertEquals(20, result.get(2).start);
        assertEquals(40, result.get(2).end);

        assertEquals(60, result.get(3).start);
        assertEquals(80, result.get(3).end);

        assertEquals(130, result.get(4).start);
        assertEquals(150, result.get(4).end);
    }
}
