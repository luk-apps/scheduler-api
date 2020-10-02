package com.wookie.lukapp.core.valueObjects;

import java.util.Comparator;

public class EventTimeFrameComparator implements Comparator<EventTimeFrame> {

    @Override
    public int compare(EventTimeFrame o1, EventTimeFrame o2) {
        return o1.getStart() - o2.getStart();
    }
}
