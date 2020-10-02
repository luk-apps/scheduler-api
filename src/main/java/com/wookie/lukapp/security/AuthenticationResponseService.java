package com.wookie.lukapp.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.wookie.lukapp.security.SecurityConstants.HEADER_STRING;
import static com.wookie.lukapp.security.SecurityConstants.TOKEN_PREFIX;

public class AuthenticationResponseService {

    String prepareAuthorizationResponse(String username, String token) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = new String();
        Map<String, String> response = new HashMap<>();
        response.put(HEADER_STRING, TOKEN_PREFIX + token);
        response.put("Username", username);
        try {
            jsonResponse = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }
}
