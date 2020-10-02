package com.wookie.lukapp;
import com.wookie.lukapp.core.aggregates.EventData;

import com.wookie.lukapp.core.services.EventDataService;
import com.wookie.lukapp.model.participant.Participant;
import com.wookie.lukapp.model.participant.Person;
import com.wookie.lukapp.model.principles.TimeAvailabilityPrinciple;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LukappApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	EventDataService eventDataService;

	@Test
	void prepareEventsDataTest() {
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

		assertEquals(eventData.get(0).getPossibleTimeFrames().get(0).getStart(), new DateTime().withTime(9,0,0,0));
		assertEquals(eventData.get(0).getPossibleTimeFrames().get(0).getEnd(), new DateTime().withTime(11,0,0,0));

		assertEquals(eventData.get(1).getPossibleTimeFrames().get(0).getStart(), new DateTime().withTime(10,0,0,0));
		assertEquals(eventData.get(1).getPossibleTimeFrames().get(0).getEnd(), new DateTime().withTime(12,0,0,0));

		assertEquals(eventData.get(2).getPossibleTimeFrames().get(0).getStart(), new DateTime().withTime(10,0,0,0));
		assertEquals(eventData.get(2).getPossibleTimeFrames().get(0).getEnd(), new DateTime().withTime(11,0,0,0));

		assertEquals(eventData.get(3).getPossibleTimeFrames().size(), 0);
	}

}
