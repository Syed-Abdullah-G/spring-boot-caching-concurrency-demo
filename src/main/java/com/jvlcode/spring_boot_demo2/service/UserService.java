package com.jvlcode.spring_boot_demo2.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.jvlcode.spring_boot_demo2.entity.UserEntity;
import com.jvlcode.spring_boot_demo2.exceptions.ResourceNotFoundException;
import com.jvlcode.spring_boot_demo2.repository.UserRepository;

// The same code that used to be inside the controller, moved here so we can add caching.
@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}

	public UserEntity createUser(UserEntity user) {
		return userRepository.save(user);
	}

	// 1st call for an id: reads MySQL and remembers the result.
	// Next calls for the same id: answered from memory, MySQL is not touched.
	@Cacheable(cacheNames = "users", key = "#id")
	public UserEntity getUserById(Long id) {
		System.out.println("Reading user " + id + " from the DATABASE");
		return findOrThrow(id);
	}

	// Saves to MySQL, then replaces the remembered copy with the new data.
	@CachePut(cacheNames = "users", key = "#id")
	public UserEntity updateUser(Long id, UserEntity user) {
		UserEntity userData = findOrThrow(id);
		userData.setEmail(user.getEmail());
		userData.setName(user.getName());
		return userRepository.save(userData);
	}

	// Deletes from MySQL, then forgets the remembered copy.
	@CacheEvict(cacheNames = "users", key = "#id")
	public void deleteUser(Long id) {
		userRepository.delete(findOrThrow(id));
	}

	private UserEntity findOrThrow(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + id));
	}
}
