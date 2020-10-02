package com.wookie.lukapp;

import com.wookie.lukapp.api.DTO.TimeFrame;
import com.wookie.lukapp.core.aggregates.EventData;
import com.wookie.lukapp.core.algorithm.ScheduleGenerator;
import com.wookie.lukapp.core.aggregates.Schedule;
import com.wookie.lukapp.core.services.EventDataService;
import com.wookie.lukapp.model.participant.Participant;
import com.wookie.lukapp.model.participant.Person;
import com.wookie.lukapp.model.principles.TimeAvailabilityPrinciple;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class FindPossibleSchedulesTests {

    @Autowired
    ScheduleGenerator scheduleGenerator;

    @Autowired
    EventDataService eventDataService;

    @Test
    void findPossibleSchedulesTest() {
        //ScheduleGenerator scheduleGenerator = new ScheduleGenerator();
        Participant p1 = new Person("Jan", "Kowalski");
        Participant p2 = new Person("Tomasz", "Nowak");
        Participant p3 = new Person("Grażyna", "Wiśniewska");
        Participant p4 = new Person("Marek", "Markowski");

        ArrayList<EventData> eventData = new ArrayList<>();
        ArrayList<Participant> group1 = new ArrayList<>();
        ArrayList<Participant> group2 = new ArrayList<>();
        ArrayList<Participant> group3 = new ArrayList<>();
        ArrayList<Participant> group4 = new ArrayList<>();

        group1.add(p1);
        group1.add(p2);

        group2.add(p3);
        group2.add(p4);

        group3.add(p1);
        group3.add(p4);

        group4.add(p2);
        group4.add(p3);

        ArrayList<TimeAvailabilityPrinciple> listOfPrinciples = new ArrayList<>();

        TimeAvailabilityPrinciple pr1 = new TimeAvailabilityPrinciple(p1, new DateTime().withTime(9,0,0,0),
                new DateTime().withTime(11,0,0,0));
        TimeAvailabilityPrinciple pr2 = new TimeAvailabilityPrinciple(p4, new DateTime().withTime(10,0,0,0),
                new DateTime().withTime(12,0,0,0));

        listOfPrinciples.add(pr1);
        listOfPrinciples.add(pr2);

        eventData.add(new EventData(group1, 60));
        eventData.add(new EventData(group2, 60));
        eventData.add(new EventData(group3, 60));
        eventData.add(new EventData(group4, 60));

        eventDataService.prepareEventsData(eventData, listOfPrinciples);

        ArrayList<TimeFrame> possibleTimeFrames = new ArrayList<>();
        possibleTimeFrames.add(new TimeFrame(new DateTime().withTime(7,0,0,0), new DateTime().withTime(15,0,0,0)));
        List<Schedule> result = scheduleGenerator.generate(eventData, possibleTimeFrames,30, 1, 10000);
        // na razie testowane manualnie
    }

    @Test
    void findPossibleSchedulesTest2() {
        Participant p1 = new Person("Jan", "Kowalski");

        ArrayList<EventData> eventData = new ArrayList<>();
        ArrayList<Participant> group1 = new ArrayList<>();

        group1.add(p1);

        ArrayList<TimeAvailabilityPrinciple> listOfPrinciples = new ArrayList<>();

        DateTime d1 = new DateTime().withTime(9,0,0,0);
        d1 = d1.plusHours(24);
        DateTime d2 = new DateTime().withTime(13,0,0,0);
        d2 = d2.plusHours(24);
        TimeAvailabilityPrinciple pr1 = new TimeAvailabilityPrinciple(p1, d1, d2);

        listOfPrinciples.add(pr1);

        eventData.add(new EventData(group1, 90));

        eventDataService.prepareEventsData(eventData, listOfPrinciples);

        ArrayList<TimeFrame> possibleTimeFrames = new ArrayList<>();
        possibleTimeFrames.add(new TimeFrame(new DateTime().withTime(8,0,0,0), new DateTime().withTime(11,30,0,0)));
        DateTime d3 = new DateTime().withTime(8,0,0,0);
        d3 = d3.plusHours(24);
        DateTime d4 = new DateTime().withTime(11,30,0,0);
        d4 = d4.plusHours(24);
        possibleTimeFrames.add(new TimeFrame(d3, d4));
        List<Schedule> result = scheduleGenerator.generate(eventData, possibleTimeFrames,30, 1, 10000);
        // na razie testowałem manualnie
    }
}
