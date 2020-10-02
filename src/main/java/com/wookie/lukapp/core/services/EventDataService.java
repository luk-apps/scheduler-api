package com.wookie.lukapp.core.services;

import com.wookie.lukapp.api.DTO.TimeFrame;
import com.wookie.lukapp.core.aggregates.EventData;
import com.wookie.lukapp.model.Event;
import com.wookie.lukapp.model.participant.Participant;
import com.wookie.lukapp.model.principles.TimeAvailabilityPrinciple;
import com.wookie.lukapp.api.event.EventRepository;
import com.wookie.lukapp.api.principle.PrincipleRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EventDataService {

    private final PrincipleRepository principleRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventDataService(PrincipleRepository principleRepository, EventRepository eventRepository) {
        this.principleRepository = principleRepository;
        this.eventRepository = eventRepository;
    }

    // TODO Refactor
    public List<EventData> createData() {
        List<Event> events = eventRepository.findAll();
        List<EventData> eventsData = new ArrayList<>();
        List<TimeAvailabilityPrinciple> principles = principleRepository.findAll();
        for(Event e : events) {
            eventsData.add(new EventData(e));
        }

        prepareEventsData(eventsData, principles);
        return eventsData;
    }

    public void prepareEventsData(List<EventData> eventsData, List<TimeAvailabilityPrinciple> principles) {
        for(EventData eventData : eventsData) {
            createEventPrincipleAssociations(eventData, principles);
            eventData.setPossibleTimeFrames(calculatePossibleTimeFrames(eventData.getTimeAvailabilityPrinciples()));
        }
    }

    private void createEventPrincipleAssociations(EventData eventData, List<TimeAvailabilityPrinciple> listOfPrinciples) {
        for(TimeAvailabilityPrinciple t : listOfPrinciples) {
            if(eventData.getParticipants().stream().anyMatch(p -> t.getParticipant().equals(p))) {
                eventData.addTimeAvailabilityPrinciple(t);
            }
        }
    }

    public TimeFrame calculatePossibleTimeFrame(List<TimeAvailabilityPrinciple> list) {
        DateTime start = null;
        DateTime end = null;
        for(TimeAvailabilityPrinciple principle : list) {
            if(start == null && end == null) {
                start = new DateTime(principle.getStartDate());
                end = new DateTime(principle.getEndDate());
            }
            else {
                if(principle.getStartDate().isAfter(start) && principle.getStartDate().isBefore(end)) {
                    start = principle.getStartDate();
                }
                if(principle.getEndDate().isAfter(start) && principle.getEndDate().isBefore(end)) {
                    end = principle.getEndDate();
                }
                if(principle.getStartDate().isAfter(end) || principle.getStartDate().isEqual(end)) {
                    return null;
                }
                if(principle.getEndDate().isBefore(start) || principle.getEndDate().isEqual(start)) {
                    return null;
                }
            }
        }
        TimeFrame result = new TimeFrame(start, end);
        return result;
    }

    // TODO UNIT TESTY - potestowane tylko manualnie
    public List<TimeFrame> calculatePossibleTimeFrames(List<TimeAvailabilityPrinciple> timeAvailabilityPrinciples) {
        List<TimeFrame> result = new ArrayList<>();
        List<Participant> uniqueParticipants = this.findUniqueParticipantsWithPrinciples(timeAvailabilityPrinciples);

        if(uniqueParticipants.isEmpty())
            return result; // return empty list

        List<List<Integer>> listOfIndexes = HelperService.findKElementCombinationsOfNElementSet(uniqueParticipants.size(), timeAvailabilityPrinciples.size());

        // iterowanie przez wszystkie kombinacje i znalezienie tych, w których wystepują wszyscy
        // unikalni uczelnicy jednocześnie. Lista indeksów zawiera wszystkie możliwe kombinacje.
        // Dla każdej sprawdzamy czy zawiera wszystkich unikalnych uczestników
        List<List<TimeAvailabilityPrinciple>> principlesGroupedByUniqueParticipants = new ArrayList<>();
        for(List<Integer> list : listOfIndexes) {
            List<TimeAvailabilityPrinciple> tempList = new ArrayList<>();
            List<Participant> foundParticipants;
            for(Integer index : list) {
                tempList.add(timeAvailabilityPrinciples.get(index));
            }
            foundParticipants = this.findUniqueParticipantsWithPrinciples(tempList);
            if(foundParticipants.size() == uniqueParticipants.size()) {
                principlesGroupedByUniqueParticipants.add(tempList);
            }
        }

        for(List<TimeAvailabilityPrinciple> list : principlesGroupedByUniqueParticipants) {
            TimeFrame timeFrame = this.calculatePossibleTimeFrame(list);
            if(timeFrame != null) {
                result.add(timeFrame);
            }
        }

        return result;
    }

    public List<Participant> findUniqueParticipantsWithPrinciples(List<TimeAvailabilityPrinciple> list) {
        Set<Participant> uniqueParticipants = new HashSet<>();
        for(TimeAvailabilityPrinciple t : list) {
            uniqueParticipants.add(t.getParticipant());
        }
        return new ArrayList<Participant>(uniqueParticipants);
    }
}
