package com.wookie.lukapp.api.user;

import com.wookie.lukapp.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppUserRepository extends MongoRepository<AppUser,String> {

    AppUser findByUsername(String username);

}
