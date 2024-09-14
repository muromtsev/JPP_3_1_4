package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AdminInitializer(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public void run(String... args) throws Exception {
        userServiceImpl.registerAdmin();
    }
}
