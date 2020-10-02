package com.wookie.lukapp;

import com.wookie.lukapp.api.DTO.TimeFrame;
import com.wookie.lukapp.core.algorithm.dateFinder.DateFinderService;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DateFinderServiceTest {

    @Autowired
    DateFinderService dateFinderService;

    @Test
    void findEarliestDateTest() {
        List<TimeFrame> pairs = new ArrayList<>();
        pairs.add(new TimeFrame(new DateTime().withTime(9,0,0,0), new DateTime().withTime(10,0,0,0)));
        pairs.add(new TimeFrame(new DateTime().withTime(8,0,0,0), new DateTime().withTime(10,0,0,0)));
        pairs.add(new TimeFrame(new DateTime().withTime(11,0,0,0), new DateTime().withTime(12,0,0,0)));

        assertEquals(new DateTime().withTime(8,0,0,0), dateFinderService.findEarliestDate(pairs));
    }
}
