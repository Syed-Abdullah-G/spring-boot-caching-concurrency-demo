package com.jvlcode.spring_boot_demo2.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jvlcode.spring_boot_demo2.entity.UserEntity;
import com.jvlcode.spring_boot_demo2.service.UserService;

@RestController
@RequestMapping("/api/users")
public class userController {

	private final UserService userService;

	public userController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<UserEntity> getUsers() {
		return userService.getAllUsers();
	}

	@PostMapping
	public UserEntity createUser(@RequestBody UserEntity user) {
		return userService.createUser(user);
	}

	@GetMapping("/{id}")
	public UserEntity getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@PutMapping("/{id}")
	public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
		return userService.updateUser(id, user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.ok().build();
	}
}
