package ru.kata.spring.boot_security.demo.controllers;

import javax.validation.Valid;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


@Controller
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	private AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
        this.roleService = roleService;
    }

	@GetMapping(value = "/admin")
	public String showAllUsers(Model model, Principal principal) {
		model.addAttribute("thisUser", userService.getUserByUsername(principal.getName()));
		model.addAttribute("users", userService.getAllUsers());
		return "all-users";
	}

	@GetMapping(value = "/admin/addNewUser")
	public String addNewUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("roles", roleService.getRoles());
		return "user";
	}

	@PostMapping(value = "/admin/saveUser")
	public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult
	 bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("roles", roleService.getRoles());
			return "user";
		}
		userService.createOrUpdateUser(user);
		return "redirect:/admin";
	}

	@GetMapping(value = "/admin/updateUser")
	public String updateUser(@RequestParam("id") int id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		model.addAttribute("roles", roleService.getRoles());
		return "user";
	}

	@PostMapping(value = "/admin/deleteUser")
	public String deleteUser(@RequestParam("id") int id) {
		userService.deleteUser(id);
		return "redirect:/admin";
	}
}