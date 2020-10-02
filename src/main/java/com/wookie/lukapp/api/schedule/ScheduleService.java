package com.wookie.lukapp.api.schedule;

import com.wookie.lukapp.api.DTO.ScheduleParameters;

import com.wookie.lukapp.core.aggregates.EventData;
import com.wookie.lukapp.core.aggregates.Schedule;
import com.wookie.lukapp.core.algorithm.ScheduleGenerator;
import com.wookie.lukapp.core.services.EventDataService;

import com.wookie.lukapp.exception.ImpossibleEventTimeFramesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleGenerator scheduleGenerator;
    private final EventDataService eventDataService;

    @Autowired
    public ScheduleService(ScheduleGenerator scheduleGenerator, EventDataService eventDataService) {
        this.scheduleGenerator = scheduleGenerator;
        this.eventDataService = eventDataService;
    }

    public List<Schedule> generateSchedules(ScheduleParameters scheduleParameters) throws ImpossibleEventTimeFramesException {

        List<Schedule> result;
        List<EventData> eventsData = eventDataService.createData();

        findImpossibleEvents(eventsData);

        if(scheduleParameters.getUseLimitedTimeFrames()) {
            result = scheduleGenerator.generate(eventsData, scheduleParameters.getTimeFrames(), scheduleParameters.getTimeStep(), scheduleParameters.getNumberOfRooms(), scheduleParameters.getMaxNumberOfResults());
        } else {
            result = scheduleGenerator.generate(eventsData, scheduleParameters.getTimeStep(), scheduleParameters.getNumberOfRooms(), scheduleParameters.getMaxNumberOfResults());
        }

        return result;
    }

    private void findImpossibleEvents(List<EventData> list) throws ImpossibleEventTimeFramesException {
        List<EventData> impossibleEvents = list.stream()
                .filter(event -> event.getPossibleTimeFrames().isEmpty())
                .collect(Collectors.toList());
        if(impossibleEvents.size() > 0) {
            List<String> impossibleEventsTitles = impossibleEvents.stream().map(event -> {
                return event.getname();
            }).collect(Collectors.toList());
            throw new ImpossibleEventTimeFramesException(impossibleEventsTitles);
        }
    }

}
