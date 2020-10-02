package com.wookie.lukapp.api.event;

import com.wookie.lukapp.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("event")
public class EventController {

    private final EventRepository eventRepository;
    private final EventService eventService;

    @Autowired
    public EventController(EventRepository eventRepository, EventService eventService) {
        this.eventRepository = eventRepository;
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        try {
            List<Event> events = new ArrayList<>();

            eventRepository.findAll().forEach(events::add);

            if (events.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/events")
    public ResponseEntity<Event> save(@RequestBody Event event) {
        try {
            Event _event = eventRepository.save(new Event(event.getName(), event.getParticipants(), event.getDurationInMinutes()));
            return new ResponseEntity<>(_event, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> delete(@PathVariable String id) {
        try {
            eventRepository.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/delete_all")
    public ResponseEntity<Event> deleteAll() {
        try {
            eventRepository.deleteAll();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }
}
