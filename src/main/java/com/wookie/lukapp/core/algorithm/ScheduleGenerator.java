package com.wookie.lukapp.core.algorithm;

import com.wookie.lukapp.api.DTO.TimeFrame;
import com.wookie.lukapp.core.aggregates.Schedule;
import com.wookie.lukapp.core.algorithm.dateFinder.DateFinderService;
import com.wookie.lukapp.core.algorithm.permutations.PermutationsService;
import com.wookie.lukapp.core.aggregates.EventData;
import com.wookie.lukapp.core.algorithm.possibleOccurences.PossibleOccurencesService;
import com.wookie.lukapp.core.services.ScheduleBuilder;
import com.wookie.lukapp.core.valueObjects.EventTimeFrame;
import com.wookie.lukapp.core.services.TimeManagementService;
import com.wookie.lukapp.core.util.TimeInterval;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleGenerator {

    private final ScheduleBuilder scheduleBuilder;
    private final DateFinderService dateFinderService;
    private final PermutationsService permutationsService;
    private final PossibleOccurencesService possibleOccurencesService;

    @Autowired
    public ScheduleGenerator(ScheduleBuilder scheduleBuilder, DateFinderService dateFinderService,
                             PermutationsService permutationsService, PossibleOccurencesService possibleOccurencesService) {
        this.scheduleBuilder = scheduleBuilder;
        this.dateFinderService = dateFinderService;
        this.permutationsService = permutationsService;
        this.possibleOccurencesService = possibleOccurencesService;
    }

    /**
     * Generate schedules within certain time frames
     * @param eventsData
     * @param possibleTimeFrames
     * @param timeStep
     * @param numberOfRooms
     * @return
     */
    public List<Schedule> generate(List<EventData> eventsData, List<TimeFrame> possibleTimeFrames, int timeStep, int numberOfRooms, int maxResults) {

        DateTime start = dateFinderService.findEarliestDate(possibleTimeFrames);
        List<TimeInterval> possibleTimeIntervals = TimeManagementService.convertListOfTimeIntervalsToIntegers(possibleTimeFrames, start);

        for(EventData eventData : eventsData) {
            eventData.setPossibleOccurrences(findPossibleOccurences(eventData, start, possibleTimeIntervals, timeStep));
        }

        List<Schedule> schedules = findPossibleSchedules(eventsData, numberOfRooms, start, maxResults);
        return schedules;
    }

    /**
     * Generates schedules without any limited time frames. The only limitations are the limitations the users have
     * @param eventsData
     * @param timeStep
     * @param numberOfRooms
     * @return
     */
    public List<Schedule> generate(List<EventData> eventsData, int timeStep, int numberOfRooms, int maxResults) {

        List<TimeFrame> allEventsTimeFrames = eventsData.stream()
                .map(EventData::getPossibleTimeFrames)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        DateTime start = dateFinderService.findEarliestDate(allEventsTimeFrames);

        for(EventData eventData : eventsData) {
            List<TimeInterval> eventPossibleTimeIntervals = TimeManagementService.convertListOfTimeIntervalsToIntegers(eventData.getPossibleTimeFrames(), start);
            eventData.setPossibleOccurrences(findPossibleOccurences(eventData, start, eventPossibleTimeIntervals, timeStep));
        }

        List<Schedule> schedules = findPossibleSchedules(eventsData, numberOfRooms, start, maxResults);
        return schedules;
    }

    private List<Schedule> findPossibleSchedules(List<EventData> eventsData, int numberOfRooms, DateTime start, int maxResults) {
        List<List<EventTimeFrame>> result = new ArrayList<>();
        List<Schedule> schedules = new ArrayList<>();
        List<EventTimeFrame> current = new ArrayList<>();
        permutationsService.generatePermutations(eventsData, result, 0, current, numberOfRooms, maxResults);

        // mamy wszystkie permutacje w results
        // teraz możemy odsiać te, które nie spełniają pozostałych warunków

        for(List<EventTimeFrame> list : result) {
            schedules.add(scheduleBuilder.prepareSchedule(list, start));
        }

        Collections.sort(schedules, Schedule.scheduleSortingComparator);

        return schedules;
    }

    private ArrayList<TimeInterval> findPossibleOccurences(EventData eventData, DateTime start,
        List<TimeInterval> generalTimeFrames, int timeStep) {

        List<TimeInterval> specificTimeIntervals = TimeManagementService.convertListOfTimeIntervalsToIntegers(eventData.getPossibleTimeFrames(), start);
        return possibleOccurencesService.findPossibleOccurences(generalTimeFrames, specificTimeIntervals, eventData.getDurationInMinutes(), timeStep);
    }
}
