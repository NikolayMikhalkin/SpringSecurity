package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService us, RoleService rs) {
        this.userService = us;
        this.roleService = rs;
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin";
    }

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/admin/update-user")
    public String updateUser(Model model, @ModelAttribute("userUpd") User user) {
        model.addAttribute("userUpd", user);
        model.addAttribute("roles", roleService.getAll());
        return "update-user";
    }

    @PostMapping("/admin/update-user")
    public String sendUpdateUser(@ModelAttribute("userUpd") User user) {
        userService.update(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/create-user")
    public String createUser(Model model) {
        model.addAttribute("userCr", new User());
        model.addAttribute("roles", roleService.getAll());
        return "create-user";
    }

    @PostMapping("/admin/create-user")
    public String sendCreateUser(@ModelAttribute("userCr") User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/remove-user")
    public String deleteUser(@RequestParam("id") Integer id) {
        userService.getDelete(id);
        return "redirect:/admin/users";
    }

}
