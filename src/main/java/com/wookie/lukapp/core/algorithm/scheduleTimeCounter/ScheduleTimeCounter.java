package com.wookie.lukapp.core.algorithm.scheduleTimeCounter;

import com.wookie.lukapp.core.valueObjects.EventDates;

import java.util.List;

public interface ScheduleTimeCounter {
    int countTotalTimePerDay(List<EventDates> list);
    int countTotalTimeByPerson(List<EventDates> list);
}
