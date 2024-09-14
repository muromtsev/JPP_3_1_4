package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerAdmin() {

        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        if (roleRepository.getRoleByName("ROLE_ADMIN") == null) {
            roleRepository.save(adminRole);
        }

        if (roleRepository.getRoleByName("ROLE_USER") == null) {
            roleRepository.save(userRole);
        }
        User admin = userRepository.findByEmail("admin@admin.com")
                .orElseGet(() -> {

                    User user = new User();
                    user.setName("admin");
                    user.setAge(33);
                    user.setEmail("admin@admin.com");
                    user.setPassword(passwordEncoder.encode("123"));
                    user.setRoles(new HashSet<>(Set.of(adminRole, userRole)));
                    return userRepository.save(user);

                });

    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User showUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(User user) {;
        userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public void update(int id, User udatedUser) {
        udatedUser.setId(id);
        userRepository.save(udatedUser);
    }

    @Transactional
    public Role getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }
}