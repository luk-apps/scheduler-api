package com.wookie.lukapp.api.principle;

import com.wookie.lukapp.model.principles.TimeAvailabilityPrinciple;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrincipleRepository extends MongoRepository<TimeAvailabilityPrinciple, String> {

}
