package com.wookie.lukapp.api.person;

import com.wookie.lukapp.api.DTO.PersonDetails;
import com.wookie.lukapp.api.event.EventRepository;
import com.wookie.lukapp.model.participant.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("person")
public class PersonController {

    private final PersonRepository personRepository;
    private final EventRepository eventRepository;
    private final PersonService personService;

    @Autowired
    public PersonController(PersonRepository personRepository, EventRepository eventRepository, PersonService personService) {
        this.personRepository = personRepository;
        this.eventRepository = eventRepository;
        this.personService = personService;
    }

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getAllPersons() {
        try {
            List<Person> persons = new ArrayList<>();

            personRepository.findAll().forEach(persons::add);

            if (persons.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(persons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<PersonDetails> getPersonDetails(@PathVariable String id) {
        try {
            Optional<Person> person = personRepository.findById(id);
            PersonDetails personDetails = new PersonDetails();
            if (person.isPresent()) {
                Person p = person.get();
                personDetails = personService.getPersonDetails(person.get());
            }

            return new ResponseEntity<>(personDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/persons")
    public ResponseEntity<Person> save(@RequestBody Person person) {
        try {
            Person _person = personRepository.save(new Person(person.getFirstName(), person.getLastName()));
            return new ResponseEntity<>(_person, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Person> update(@PathVariable String id, @RequestBody Person person) {
        Optional<Person> personData = personRepository.findById(id);

        if(personData.isPresent()) {
            Person _person = personData.get();
            _person.setFirstName(person.getFirstName());
            _person.setLastName(person.getLastName());
            Person result = personRepository.save(_person);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> delete(@PathVariable String id) {
        try {
            Optional<Person> _person = personRepository.findById(id);
            if(_person.isPresent())
                personService.deletePerson(_person.get());
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/delete_all")
    public ResponseEntity<Person> deleteAll() {
        try {
            personRepository.deleteAll();
            eventRepository.deleteAll();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
}
