package com.wookie.lukapp.api.person;

import com.wookie.lukapp.model.participant.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonRepository extends MongoRepository<Person, String> {
    public Person findByFirstName(String firstName);
    public List<Person> findByLastName(String lastName);
}
