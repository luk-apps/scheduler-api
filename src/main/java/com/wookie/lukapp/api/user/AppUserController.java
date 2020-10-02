package com.wookie.lukapp.api.user;

import com.wookie.lukapp.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api")
@RestController
public class AppUserController {

    private AppUserRepository appUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppUserController(AppUserRepository appUserRepository,
                             BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    @GetMapping("/users")
    public ResponseEntity<AppUser> getUserById(@RequestParam(required = false) Long id) {
        return new ResponseEntity<>(new AppUser("a"), HttpStatus.FOUND);
    }

    @GetMapping
    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser appUser) {
        logger.info("Attempting to create new AppUser");
        try {
            AppUser newAppUser = appUserRepository.save(new AppUser(appUser.getUsername()));
            return new ResponseEntity<>(appUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody AppUser appUser) {
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUserRepository.save(appUser);
    }

}
