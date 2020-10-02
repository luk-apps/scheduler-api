package com.wookie.lukapp.core.valueObjects;

import com.wookie.lukapp.model.Event;
import org.joda.time.DateTime;

/**
 * This is similar to EventTimeFrame but dates are written in DateTime format.
 */
public class EventDates {
    Event event;
    DateTime start;
    DateTime end;

    public EventDates(Event event, DateTime start, DateTime end) {
        this.event = event;
        this.start = start;
        this.end = end;
    }

    public Event getEvent() {
        return event;
    }

    public DateTime getStart() {
        return start;
    }

    public DateTime getEnd() {
        return end;
    }
}
