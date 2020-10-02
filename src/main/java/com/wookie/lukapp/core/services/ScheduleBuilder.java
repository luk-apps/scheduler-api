package com.wookie.lukapp.core.services;

import com.wookie.lukapp.core.aggregates.Schedule;
import com.wookie.lukapp.core.algorithm.dateFinder.DateFinderService;
import com.wookie.lukapp.core.algorithm.scheduleTimeCounter.ScheduleTimeCounter;
import com.wookie.lukapp.core.valueObjects.EventDates;
import com.wookie.lukapp.core.valueObjects.EventTimeFrame;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleBuilder {

    private final DateFinderService dateFinderService;
    private final ScheduleTimeCounter scheduleTimeCounter;

    @Autowired
    public ScheduleBuilder(DateFinderService dateFinderService, ScheduleTimeCounter scheduleTimeCounter) {
        this.dateFinderService = dateFinderService;
        this.scheduleTimeCounter = scheduleTimeCounter;
    }

    /**
     * Converts list of eventTimeFrame objects to final schedule object.
     * @param eventTimeFrames
     * @param start
     * @return
     */
    public Schedule prepareSchedule(List<EventTimeFrame> eventTimeFrames, DateTime start) {
        List<EventDates> list = new ArrayList();
        for(EventTimeFrame e : eventTimeFrames) {
            DateTime startDate = TimeManagementService.convertIntToTime(e.getStart(), start);
            DateTime endDate = TimeManagementService.convertIntToTime(e.getEnd(), start);
            list.add(new EventDates(e.getEventData().getEvent(), startDate, endDate));
        }
        // choose one of two strategies of counting time
        int totalTime = scheduleTimeCounter.countTotalTimeByPerson(list);
        return new Schedule(list, totalTime);
    }
}
