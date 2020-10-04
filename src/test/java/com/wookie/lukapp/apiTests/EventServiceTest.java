package com.wookie.lukapp.apiTests;

import com.wookie.lukapp.api.event.EventRepository;
import com.wookie.lukapp.api.event.EventService;
import com.wookie.lukapp.api.person.PersonRepository;
import com.wookie.lukapp.model.Event;
import com.wookie.lukapp.model.participant.Participant;
import com.wookie.lukapp.model.participant.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EventServiceTest {

    private EventRepository eventRepository;
    private PersonRepository personRepository;
    private EventService eventService;

    @BeforeEach
    void setup() {
        eventRepository = Mockito.mock(EventRepository.class);
        personRepository = Mockito.mock(PersonRepository.class);
        eventService = new EventService(eventRepository);

        List<Event> events = new ArrayList<>();
        ArrayList<Participant> group1 = new ArrayList<>();
        Person p1 = new Person("A", "A");
        Person p2 = new Person("B", "B");
        Person p3 = new Person("C", "C");
        group1.add(p1);
        group1.add(p2);
        group1.add(p3);
        events.add(new Event("E1", group1, 60));

        ArrayList<Participant> group2 = new ArrayList<>();
        group2.add(p1);
        group2.add(p2);

        events.add(new Event("E2", group2, 60));

        when(eventRepository.findAll())
                .thenReturn(events);
        when(personRepository.findById("A"))
                .thenReturn(Optional.of(p1));
        when(personRepository.findById("C"))
                .thenReturn(Optional.of(p3));
    }

    @Test
    void deletePersonFromAssociatedEventsTest() {
        Optional<Person> person = personRepository.findById("A");
        eventService.deletePersonFromAssociatedEvents(person.get());
        List<Event> result = eventRepository.findAll();
        List<Event> eventsContainingPerson = result.stream().filter(r ->
                r.getParticipants().contains(person.get())).collect(Collectors.toList());

        assertEquals(2, result.size());
        assertEquals(0, eventsContainingPerson.size());
    }

    @Test
    void findAllEventsByPersonTest() {
        Optional<Person> person = personRepository.findById("C");
        List<Event> result = eventService.findAllEventsByPerson(person.get());

        assertEquals(1, result.size());
        assertTrue(result.get(0).getParticipants().contains(person.get()));
    }
}
