package com.wookie.lukapp.model.principles;

import com.wookie.lukapp.model.participant.Participant;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;


public class TimeAvailabilityPrinciple implements Principle {

    @Id
    public String id;
    private Participant participant;

    private DateTime startDate;
    private DateTime endDate;

    public TimeAvailabilityPrinciple() {}

    public TimeAvailabilityPrinciple(Participant participant, DateTime startDate, DateTime endDate) {
        this.participant = participant;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }
}
