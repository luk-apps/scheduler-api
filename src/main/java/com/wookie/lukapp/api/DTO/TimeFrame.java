package com.wookie.lukapp.api.DTO;

import org.joda.time.DateTime;

public class TimeFrame {
    private DateTime start;
    private DateTime end;

    public TimeFrame() {}

    public TimeFrame(DateTime start, DateTime end) {
        this.start = start;
        this.end = end;
    }

    public DateTime getStart() {
        return start;
    }

    public DateTime getEnd() {
        return end;
    }
}
