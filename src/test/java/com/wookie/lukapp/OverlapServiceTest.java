package com.wookie.lukapp;

import com.wookie.lukapp.core.aggregates.EventData;
import com.wookie.lukapp.core.algorithm.overlap.OverlapService;
import com.wookie.lukapp.core.valueObjects.EventTimeFrame;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OverlapServiceTest {

    @Autowired
    OverlapService overlapService;

    @Test
    void doEventsOverlapTest() {
        List<EventTimeFrame> list = new ArrayList<>();

        EventData e1 = new EventData();
        EventData e2 = new EventData();
        EventData e3 = new EventData();

        list.add(new EventTimeFrame(e1, 60, 120));
        list.add(new EventTimeFrame(e2, 120, 140));
        list.add(new EventTimeFrame(e3, 140, 150));

        assertEquals(overlapService.doEventsOverlap(list), false);
    }

    @Test
    void doEventsOverlapTest2() {
        List<EventTimeFrame> list = new ArrayList<>();

        EventData e1 = new EventData();
        EventData e2 = new EventData();
        EventData e3 = new EventData();

        list.add(new EventTimeFrame(e2, 120, 140));
        list.add(new EventTimeFrame(e1, 60, 121));
        list.add(new EventTimeFrame(e3, 140, 150));

        assertEquals(overlapService.doEventsOverlap(list), true);
    }

    @Test
    void doEventsOverlapTest3() {
        List<EventTimeFrame> list = new ArrayList<>();

        EventData e1 = new EventData();
        EventData e2 = new EventData();
        EventData e3 = new EventData();

        list.add(new EventTimeFrame(e2, 120, 140));
        list.add(new EventTimeFrame(e1, 60, 120));
        list.add(new EventTimeFrame(e3, 270, 300));

        assertEquals(overlapService.doEventsOverlap(list),false);
    }

    @Test
    void doEventsOverlapTest4() {
        List<EventTimeFrame> list = new ArrayList<>();

        EventData e1 = new EventData();
        EventData e2 = new EventData();
        EventData e3 = new EventData();

        list.add(new EventTimeFrame(e2, 120, 140));
        list.add(new EventTimeFrame(e1, 60, 140));
        list.add(new EventTimeFrame(e3, 270, 300));

        assertEquals(overlapService.doEventsOverlap(list), true);
    }
}
