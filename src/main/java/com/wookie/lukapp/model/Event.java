package com.wookie.lukapp.model;

import com.wookie.lukapp.model.participant.Participant;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Event {

    @Id
    public String id;
    private String name;
    private ArrayList<Participant> participants;
    private int durationInMinutes;

    public Event() {}

    public Event(String name, ArrayList<Participant> participants, int durationInMinutes) {
        this.name = name;
        this.participants = participants;
        this.durationInMinutes = durationInMinutes;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }

    public void removeParticipant(Participant participant) {
        participants.remove(participant);
    }
}
