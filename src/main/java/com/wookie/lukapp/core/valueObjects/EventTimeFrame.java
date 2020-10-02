package com.wookie.lukapp.core.valueObjects;

import com.wookie.lukapp.core.aggregates.EventData;

public class EventTimeFrame {

    private EventData eventData;
    private int start;
    private int end;

    public EventTimeFrame(EventData eventData, int start, int end) {
        this.eventData = eventData;
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public EventData getEventData() {
        return eventData;
    }

}
