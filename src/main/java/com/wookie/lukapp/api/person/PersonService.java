package com.wookie.lukapp.api.person;

import com.wookie.lukapp.api.DTO.PersonDetails;
import com.wookie.lukapp.model.participant.Person;
import com.wookie.lukapp.api.event.EventService;
import com.wookie.lukapp.api.principle.PrincipleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PrincipleService principleService;
    private final EventService eventService;
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PrincipleService principleService, EventService eventService, PersonRepository personRepository) {
        this.principleService = principleService;
        this.eventService = eventService;
        this.personRepository = personRepository;
    }

    public PersonDetails getPersonDetails(Person person) {
        return new PersonDetails(person, principleService.getPrinciplesByPerson(person));
    }

    public void deletePerson(Person person) {
        principleService.deletePrinciplesByPerson(person);
        eventService.deleteEventsByPerson(person);
        eventService.deletePersonFromAssociatedEvents(person);
        personRepository.delete(person);
    }
}
