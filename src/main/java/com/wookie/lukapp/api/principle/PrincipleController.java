package com.wookie.lukapp.api.principle;

import com.wookie.lukapp.model.principles.TimeAvailabilityPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("principle")
public class PrincipleController {

    private final PrincipleRepository principleRepository;
    private final PrincipleService principleService;

    @Autowired
    public PrincipleController(PrincipleRepository principleRepository, PrincipleService principleService) {
        this.principleRepository = principleRepository;
        this.principleService = principleService;
    }

    @GetMapping("/principles")
    public ResponseEntity<List<TimeAvailabilityPrinciple>> getAllPrinciples() {
        try {
            List<TimeAvailabilityPrinciple> principles = new ArrayList<>();

            principleRepository.findAll().forEach(principles::add);

            if (principles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(principles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/principles")
    public ResponseEntity<TimeAvailabilityPrinciple> save(@RequestBody TimeAvailabilityPrinciple principle) {
        try {
            TimeAvailabilityPrinciple _principle = principleRepository
                    .save(new TimeAvailabilityPrinciple(principle.getParticipant(), principle.getStartDate(), principle.getEndDate()));
            return new ResponseEntity<>(_principle, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/multiple_principles")
    public ResponseEntity<List<TimeAvailabilityPrinciple>> multipleSave(@RequestBody List<TimeAvailabilityPrinciple> principles) {
        try {
            List<TimeAvailabilityPrinciple> _principles = principleRepository.saveAll(principles);
            return new ResponseEntity<>(_principles, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/update_by_person/{personId}")
    public ResponseEntity<List<TimeAvailabilityPrinciple>> updateByPerson(@PathVariable String personId, @RequestBody List<TimeAvailabilityPrinciple> principles) {
        try {
            List<TimeAvailabilityPrinciple> _principles = principleService.updatePrinciplesByPerson(personId, principles);
            return new ResponseEntity<>(_principles, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TimeAvailabilityPrinciple> delete(@PathVariable String id) {
        try {
            principleRepository.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

}
