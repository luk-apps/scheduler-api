package com.wookie.lukapp.core.algorithm.possibleOccurences;

import com.wookie.lukapp.core.services.TimeManagementService;
import com.wookie.lukapp.core.util.TimeInterval;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PossibleOccurencesSeriviceImpl implements PossibleOccurencesService {

    /**
     * This version of method finds possible time intervals for a list of possible time frames. It might be used
     * when you want to find possible occurences for event for few days in a row with specific time limitations each day.
     * @param possibleTimeIntervals list of time intervals that are generally available to create a schedule
     * @param eventPossibleTimeIntervals list of available time intervals for specific eventData
     * @param durationInMinutes eventData duration
     * @param timeStep
     * @return
     */
    @Override
    public ArrayList<TimeInterval> findPossibleOccurences(List<TimeInterval> possibleTimeIntervals, List<TimeInterval> eventPossibleTimeIntervals, int durationInMinutes, int timeStep) {
        ArrayList<TimeInterval> timeIntervals = new ArrayList<>();

        for(TimeInterval t : possibleTimeIntervals) {
            int eventStart = t.start;
            int upperTimeLimit = t.end;
            while(eventStart + durationInMinutes <= upperTimeLimit) {
                if(TimeManagementService.checkIfTimeIntervalMatchesListOfTimeIntervals(eventStart, eventStart + durationInMinutes, eventPossibleTimeIntervals) == true) {
                    timeIntervals.add(new TimeInterval(eventStart, eventStart + durationInMinutes));
                }
                eventStart += timeStep;
            }
        }

        return timeIntervals;
    }
}
