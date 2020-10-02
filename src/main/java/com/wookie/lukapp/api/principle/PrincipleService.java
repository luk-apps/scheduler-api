package com.wookie.lukapp.api.principle;

import com.wookie.lukapp.model.participant.Person;
import com.wookie.lukapp.model.principles.Principle;
import com.wookie.lukapp.model.principles.TimeAvailabilityPrinciple;
import com.wookie.lukapp.api.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrincipleService {

    private final PrincipleRepository principleRepository;
    private final PersonRepository personRepository;

    @Autowired
    public PrincipleService(PrincipleRepository principleRepository, PersonRepository personRepository) {
        this.principleRepository = principleRepository;
        this.personRepository = personRepository;
    }

    public List<TimeAvailabilityPrinciple> getPrinciplesByPerson(Person person) {
        return filterPrinciplesByPerson(principleRepository.findAll(), person);
    }

    public List<TimeAvailabilityPrinciple> filterPrinciplesByPerson(List<TimeAvailabilityPrinciple> list, Person person) {
        return list.stream().filter(p ->
                p.getParticipant().getId().equals(person.id))
                .collect(Collectors.toList());
    }

    public List<TimeAvailabilityPrinciple> updatePrinciplesByPerson(String personId, List<TimeAvailabilityPrinciple> list) {
        Optional<Person> person = personRepository.findById(personId);
        List<TimeAvailabilityPrinciple> currentPrinciples = new ArrayList<>();
        if(person.isPresent()) {
            currentPrinciples = filterPrinciplesByPerson(principleRepository.findAll(), person.get());
            principleRepository.deleteAll(currentPrinciples);
        }

        List<TimeAvailabilityPrinciple> _principles = principleRepository.saveAll(list);
        return _principles;
    }

    public void deletePrinciplesByPerson(Person person) {
        List<TimeAvailabilityPrinciple> principles = getPrinciplesByPerson(person);
        for(TimeAvailabilityPrinciple p : principles) {
            principleRepository.delete(p);
        }
    }
}
