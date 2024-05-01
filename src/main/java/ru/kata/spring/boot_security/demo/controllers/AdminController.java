package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	private AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
        this.roleService = roleService;
    }

	@GetMapping
	public String showAllUsers(Model model, Principal principal) {
		model.addAttribute("thisUser", userService.getUserByUsername(principal.getName()));
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("allRoles", roleService.getRoles());
		model.addAttribute("newUser", new User());
		return "admin-panel";
	}

	@PostMapping(value = "/saveUser")
	public String saveUser(@ModelAttribute("user") User user) {
		userService.createOrUpdateUser(user);
		return "redirect:/admin";
	}


	@PostMapping(value = "/deleteUser")
	public String deleteUser(@RequestParam("id") int id) {
		userService.deleteUser(id);
		return "redirect:/admin";
	}
}