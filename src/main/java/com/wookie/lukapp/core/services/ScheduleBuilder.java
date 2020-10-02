package com.wookie.lukapp.core.services;

import com.wookie.lukapp.api.DTO.TimeFrame;
import com.wookie.lukapp.core.aggregates.Schedule;
import com.wookie.lukapp.core.algorithm.dateFinder.DateFinderService;
import com.wookie.lukapp.core.valueObjects.EventDates;
import com.wookie.lukapp.core.valueObjects.EventTimeFrame;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleBuilder {

    private final DateFinderService dateFinderService;

    @Autowired
    public ScheduleBuilder(DateFinderService dateFinderService) {
        this.dateFinderService = dateFinderService;
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
        int totalTime = countTotalTime(list);
        return new Schedule(list, totalTime);
    }

    private int countTotalTime(List<EventDates> list) {
        int sum = 0;
        MultiValueMap<LocalDate, TimeFrame> map = new LinkedMultiValueMap<>();
        for(EventDates eventDates : list) {
            map.add(new LocalDate(eventDates.getStart().getYear(),
                    eventDates.getStart().getMonthOfYear(),
                    eventDates.getStart().getDayOfMonth()),
                    new TimeFrame(eventDates.getStart(), eventDates.getEnd()));
        }

        for(LocalDate eventDates : map.keySet()){
            DateTime earliest = dateFinderService.findEarliestDate(map.get(eventDates));
            DateTime latest = dateFinderService.findLatesDate(map.get(eventDates));
            sum += Minutes.minutesBetween(earliest, latest).getMinutes();
        }

        return sum;
    }
}
