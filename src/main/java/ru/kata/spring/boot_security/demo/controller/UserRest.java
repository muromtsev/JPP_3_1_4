package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserRest {

    private final UserDetailsService userDetailsService;

    public UserRest(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public User getUser(Principal principal) {
        return (User) userDetailsService.loadUserByUsername(principal.getName());
    }
}
