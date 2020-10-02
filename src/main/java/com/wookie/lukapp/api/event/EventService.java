package com.wookie.lukapp.api.event;

import com.wookie.lukapp.model.participant.Person;
import com.wookie.lukapp.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * deletes only events where selected person is the only one participating
     * @param person
     */
    public void deleteEventsByPerson(Person person) {
        List<Event> events = findAllEventsByPerson(person);
        for(Event e : events) {
            if(e.getParticipants().size() == 1) {
                eventRepository.delete(e);
            }
        }
    }

    public void deletePersonFromAssociatedEvents(Person person) {
        List<Event> events = findAllEventsByPerson(person);
        for(Event e : events) {
            e.removeParticipant(person);
            eventRepository.save(e);
        }
    }

    public List<Event> findAllEventsByPerson(Person person) {
        return eventRepository.findAll()
                .stream()
                .filter(e -> e.getParticipants().contains(person))
                .collect(Collectors.toList());
    }
}
