package com.wookie.lukapp.api.event;

import com.wookie.lukapp.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
}
