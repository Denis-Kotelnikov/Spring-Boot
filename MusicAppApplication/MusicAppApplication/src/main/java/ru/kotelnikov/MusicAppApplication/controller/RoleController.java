package ru.kotelnikov.MusicAppApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kotelnikov.MusicAppApplication.entity.Role;
import ru.kotelnikov.MusicAppApplication.entity.User;
import ru.kotelnikov.MusicAppApplication.repository.RoleRepository;
import ru.kotelnikov.MusicAppApplication.repository.UserRepository;

@Controller
public class RoleController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/manage-roles")
    public String manageRoles(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "manage-roles";
    }

    @PostMapping("/assign-role")
    public String assignRole(@RequestParam("userId") Long userId, @RequestParam("roleName") String roleName, Model model) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            model.addAttribute("error", "Пользователь не найден");
            model.addAttribute("users", userRepository.findAll());
            return "manage-roles";
        }

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            model.addAttribute("error", "Роль " + roleName + " не найдена");
            model.addAttribute("users", userRepository.findAll());
            return "manage-roles";
        }

        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userRepository.save(user);
        }

        return "redirect:/manage-roles";
    }
}