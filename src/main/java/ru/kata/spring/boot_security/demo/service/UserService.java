package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User showUser(int id);

    void delete(int id);

    void save(User user);

    void update(int id, User udatedUser);

    List<Role> getAllRoles();

    Role getRoleByName(String role);
}
