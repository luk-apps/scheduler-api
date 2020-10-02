package com.wookie.lukapp.model.participant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Person.class)
public interface Participant {

    public String getId();
    public void print();
}
