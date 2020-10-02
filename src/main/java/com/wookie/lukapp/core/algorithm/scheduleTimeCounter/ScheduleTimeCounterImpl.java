package com.wookie.lukapp.core.algorithm.scheduleTimeCounter;

import com.wookie.lukapp.api.DTO.TimeFrame;
import com.wookie.lukapp.core.algorithm.dateFinder.DateFinderService;
import com.wookie.lukapp.core.valueObjects.EventDates;
import com.wookie.lukapp.model.participant.Participant;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.AbstractMap;
import java.util.List;

@Service
public class ScheduleTimeCounterImpl implements ScheduleTimeCounter {

    private final DateFinderService dateFinderService;

    @Autowired
    public ScheduleTimeCounterImpl(DateFinderService dateFinderService) {
        this.dateFinderService = dateFinderService;
    }

    @Override
    public int countTotalTimePerDay(List<EventDates> list) {
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

    @Override
    public int countTotalTimeByPerson(List<EventDates> list) {
        int sum = 0;
        MultiValueMap<AbstractMap.SimpleEntry<Participant, LocalDate>, TimeFrame> timePerPersonADay = new LinkedMultiValueMap<>();
        for(EventDates eventDates : list) {
            eventDates.getEvent().getParticipants().forEach(participant -> {
                AbstractMap.SimpleEntry<Participant, LocalDate> entry
                        = new AbstractMap.SimpleEntry<>(participant, new LocalDate(eventDates.getStart().getYear(),
                        eventDates.getStart().getMonthOfYear(),
                        eventDates.getStart().getDayOfMonth()));
                timePerPersonADay.add(entry, new TimeFrame(eventDates.getStart(), eventDates.getEnd()));
            });
        }

        for(AbstractMap.SimpleEntry<Participant, LocalDate> personADay : timePerPersonADay.keySet()){
            DateTime earliest = dateFinderService.findEarliestDate(timePerPersonADay.get(personADay));
            DateTime latest = dateFinderService.findLatesDate(timePerPersonADay.get(personADay));
            sum += Minutes.minutesBetween(earliest, latest).getMinutes();
        }

        return sum;
    }
}
