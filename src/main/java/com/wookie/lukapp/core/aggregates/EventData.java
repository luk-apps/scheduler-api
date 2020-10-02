package com.wookie.lukapp.core.aggregates;

import com.wookie.lukapp.api.DTO.TimeFrame;
import com.wookie.lukapp.core.util.TimeInterval;
import com.wookie.lukapp.model.Event;
import com.wookie.lukapp.model.participant.Participant;
import com.wookie.lukapp.model.principles.TimeAvailabilityPrinciple;

import java.util.*;

public class EventData {

    private Event event;
    private List<TimeInterval> possibleOccurrences;
    private List<TimeFrame> possibleTimeFrames = new ArrayList<>();
    private List<TimeAvailabilityPrinciple> timeAvailabilityPrinciples = new ArrayList<>();

    public EventData() {}

    public EventData(ArrayList<Participant> participants, int durationInMinutes) {
        event = new Event();
        this.event.setParticipants(participants);
        this.event.setDurationInMinutes(durationInMinutes);
    }

    public EventData(Event event) {
        this.event = event;
    }

    public List<Participant> getParticipants() {
        return this.event.getParticipants();
    }

    public String getname() {
        return this.event.getName();
    }

    public List<TimeInterval> getPossibleOccurrences() {
        return possibleOccurrences;
    }

    public void setPossibleOccurrences(List<TimeInterval> possibleOccurrences) {
        this.possibleOccurrences = possibleOccurrences;
    }

    public List<TimeFrame> getPossibleTimeFrames() {
        return possibleTimeFrames;
    }

    public void setPossibleTimeFrames(List<TimeFrame> possibleTimeFrames) {
        this.possibleTimeFrames = possibleTimeFrames;
    }

    public List<TimeAvailabilityPrinciple> getTimeAvailabilityPrinciples() {
        return timeAvailabilityPrinciples;
    }

    public int getDurationInMinutes() {
        return this.event.getDurationInMinutes();
    }

    public void addTimeAvailabilityPrinciple(TimeAvailabilityPrinciple principle) {
        this.getTimeAvailabilityPrinciples().add(principle);
    }

    public Event getEvent() {
        return event;
    }
}
