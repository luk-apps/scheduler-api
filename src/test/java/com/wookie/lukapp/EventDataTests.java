package com.wookie.lukapp;

import com.wookie.lukapp.api.DTO.TimeFrame;
import com.wookie.lukapp.core.aggregates.EventData;
import com.wookie.lukapp.core.services.EventDataService;
import com.wookie.lukapp.model.participant.Participant;
import com.wookie.lukapp.model.participant.Person;
import com.wookie.lukapp.model.principles.TimeAvailabilityPrinciple;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EventDataTests {

    @Autowired
    EventDataService eventDataService;

    @Test
    void calculatePossibleTimeFrameTest() {
        EventData eventData = new EventData();
        Participant p1 = new Person("a", "a");
        Participant p2 = new Person("b", "b");

        eventData.addTimeAvailabilityPrinciple(new TimeAvailabilityPrinciple(p1, new DateTime().withTime(9,0,0,0),
                new DateTime().withTime(11,0,0,0)));
        eventData.addTimeAvailabilityPrinciple(new TimeAvailabilityPrinciple(p2, new DateTime().withTime(10,0,0,0),
                new DateTime().withTime(12,0,0,0)));
        eventData.setPossibleTimeFrames(eventDataService.calculatePossibleTimeFrames(eventData.getTimeAvailabilityPrinciples()));

        List<TimeFrame> result = eventData.getPossibleTimeFrames();
        assertEquals(1, result.size());
        assertEquals(new DateTime().withTime(10,0,0,0), result.get(0).getStart());
        assertEquals(new DateTime().withTime(11,0,0,0), result.get(0).getEnd());
    }


    @Test
    void calculatePossibleTimeFramesTest() {
        EventData eventData = new EventData();
        Participant p1 = new Person("a", "a");
        Participant p2 = new Person("b", "b");

        eventData.addTimeAvailabilityPrinciple(new TimeAvailabilityPrinciple(p1, new DateTime().withTime(9,0,0,0),
                new DateTime().withTime(11,0,0,0)));
        eventData.addTimeAvailabilityPrinciple(new TimeAvailabilityPrinciple(p2, new DateTime().withTime(10,0,0,0),
                new DateTime().withTime(12,0,0,0)));
        eventData.addTimeAvailabilityPrinciple(new TimeAvailabilityPrinciple(p1, new DateTime().withTime(15,0,0,0),
                new DateTime().withTime(17,0,0,0)));
        eventData.addTimeAvailabilityPrinciple(new TimeAvailabilityPrinciple(p2, new DateTime().withTime(16,0,0,0),
                new DateTime().withTime(18,0,0,0)));
        eventData.setPossibleTimeFrames(eventDataService.calculatePossibleTimeFrames(eventData.getTimeAvailabilityPrinciples()));

        List<TimeFrame> result = eventData.getPossibleTimeFrames();
        assertEquals(2, eventData.getPossibleTimeFrames().size());

    }

    @Test
    void findUniqueParticipantsWithPrinciplesTest() {
        EventData eventData = new EventData();
        Participant p1 = new Person("a", "a");
        Participant p2 = new Person("b", "b");

        eventData.addTimeAvailabilityPrinciple(new TimeAvailabilityPrinciple(p1, new DateTime().withTime(9,0,0,0),
                new DateTime().withTime(11,0,0,0)));
        eventData.addTimeAvailabilityPrinciple(new TimeAvailabilityPrinciple(p2, new DateTime().withTime(10,0,0,0),
                new DateTime().withTime(12,0,0,0)));
        eventData.addTimeAvailabilityPrinciple(new TimeAvailabilityPrinciple(p1, new DateTime().withTime(15,0,0,0),
                new DateTime().withTime(17,0,0,0)));
        eventData.addTimeAvailabilityPrinciple(new TimeAvailabilityPrinciple(p2, new DateTime().withTime(16,0,0,0),
                new DateTime().withTime(18,0,0,0)));

        List<Participant> result = eventDataService.findUniqueParticipantsWithPrinciples(eventData.getTimeAvailabilityPrinciples());
        assertEquals(2, result.size());
    }


}
