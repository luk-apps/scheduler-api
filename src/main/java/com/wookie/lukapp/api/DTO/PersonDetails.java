package com.wookie.lukapp.api.DTO;

import com.wookie.lukapp.model.participant.Person;
import com.wookie.lukapp.model.principles.TimeAvailabilityPrinciple;

import java.util.List;

public class PersonDetails {

    private Person person;
    private List<TimeAvailabilityPrinciple> principles;

    public PersonDetails() {}

    public PersonDetails(Person person, List<TimeAvailabilityPrinciple> principles) {
        this.person = person;
        this.principles = principles;
    }

    public Person getPerson() {
        return person;
    }

    public List<TimeAvailabilityPrinciple> getPrinciples() {
        return principles;
    }
}
