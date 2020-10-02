package com.wookie.lukapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.wookie.lukapp.model.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.auth0.jwt.JWT;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.wookie.lukapp.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            AppUser creds = new ObjectMapper()
                    .readValue(req.getInputStream(), AppUser.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String userName = ((User) auth.getPrincipal()).getUsername();
        String token = JWT.create()
                .withSubject(userName)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

        AuthenticationResponseService authenticationResponseService = new AuthenticationResponseService();
        String jsonResponse = authenticationResponseService.prepareAuthorizationResponse(userName, token);
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.getWriter().write(jsonResponse);
    }


}
