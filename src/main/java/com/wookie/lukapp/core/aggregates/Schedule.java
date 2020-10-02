package com.wookie.lukapp.core.aggregates;

import com.wookie.lukapp.core.valueObjects.EventDates;

import java.util.Comparator;
import java.util.List;

/**
 * Schedule is a final product of algorithm. It consists of list of time frames for each eventData.
 */
public class Schedule {

    private List<EventDates> eventsTimeFrames;

    /**
     * Sum of time all events in schedule take each day
     */
    int totalTime;
    public Schedule() {}

    public Schedule(List<EventDates> eventsTimeFrames) {
        this.eventsTimeFrames = eventsTimeFrames;
    }

    public Schedule(List<EventDates> eventsTimeFrames, int totalTime) {
        this.eventsTimeFrames = eventsTimeFrames;
        this.totalTime = totalTime;
    }

    public List<EventDates> getEventsTimeFrames() {
        return eventsTimeFrames;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public static Comparator<Schedule> scheduleSortingComparator = Comparator.comparing(Schedule::getTotalTime);
}
