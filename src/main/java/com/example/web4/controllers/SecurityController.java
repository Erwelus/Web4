package com.example.web4.controllers;

import com.example.web4.dto.AuthDto;
import com.example.web4.model.User;
import com.example.web4.security.JwtProvider;
import com.example.web4.service.UserService;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NonUniqueResultException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth/")
public class SecurityController {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public SecurityController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserService userService){
        this.authenticationManager=authenticationManager;
        this.jwtProvider=jwtProvider;
        this.userService=userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthDto authDto){
        try {
            String username = authDto.getUsername();
            User user = userService.findByUsername(username);

            if (user == null) throw new UsernameNotFoundException("User with username " + username + " not found");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authDto.getPassword()));
            String token = jwtProvider.createToken(username);

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("refresh_token", user.getRefreshToken());
            response.put("token", token);

            return ResponseEntity.ok(response);
        }catch (UsernameNotFoundException ex){
            Map<Object, Object> response = new HashMap<>();
            response.put("description", "User with username " + authDto.getUsername() + " not found");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }catch (AuthenticationException ex){
            Map<Object, Object> response = new HashMap<>();
            response.put("description", "Wrong password");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AuthDto authDto){

        try{
            String username = authDto.getUsername();
            if(userService.findByUsername(username)!=null){
                throw new NonUniqueResultException("User with such username has been already registered");
            }
            User user = userService.register(new User(username, authDto.getPassword()));
            String token = jwtProvider.createToken(username);
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("refresh_token", user.getRefreshToken());
            response.put("token", token);

            return ResponseEntity.ok(response);
        }catch (NonUniqueResultException | IncorrectResultSizeDataAccessException ex){
            Map<Object, Object> response = new HashMap<>();
            response.put("description", "User with username "+authDto.getUsername()+" has already been registered");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
}
