package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roleList", userService.getAllRoles());
        return "admin/admin";
    }

    @GetMapping("/{id}")
    public String showUser(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "admin/admin";
    }


    @PostMapping()
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "nameRoles") String[] roles) {
        Set<Role> roles1 = new HashSet<>();
        for (String role : roles) {
            roles1.add(userService.getRoleByName(role));
        }
        user.setRoles(roles1);
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable("id") int id,
                             @ModelAttribute("user") User user,
                             @RequestParam(value = "editRoles") String[] roles) {
        Set<Role> roles1 = new HashSet<>();
        for (String role : roles) {
            roles1.add(userService.getRoleByName(role));
        }
        user.setRoles(roles1);
        userService.update(id, user);
        return "redirect:/admin";
    }

    @GetMapping("{id}/remove")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
