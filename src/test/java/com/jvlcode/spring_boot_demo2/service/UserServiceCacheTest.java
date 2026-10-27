package com.jvlcode.spring_boot_demo2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.jvlcode.spring_boot_demo2.entity.UserEntity;
import com.jvlcode.spring_boot_demo2.repository.UserRepository;

// Uses a fake repository, so MySQL is not needed. We count how many times the "database" is called.
@SpringJUnitConfig(UserServiceCacheTest.TestConfig.class)
class UserServiceCacheTest {

	@Configuration
	@EnableCaching
	static class TestConfig {
		@Bean
		CacheManager cacheManager() {
			return new ConcurrentMapCacheManager("users");
		}

		@Bean
		UserRepository userRepository() {
			return mock(UserRepository.class);
		}

		@Bean
		UserService userService(UserRepository repo) {
			return new UserService(repo);
		}
	}

	@Autowired UserService userService;
	@Autowired UserRepository userRepository;
	@Autowired CacheManager cacheManager;

	@BeforeEach
	void setUp() {
		reset(userRepository);
		cacheManager.getCache("users").clear();
	}

	@Test
	void secondLookupComesFromCache() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(new UserEntity(1L, "Asha", "asha@example.com")));

		userService.getUserById(1L);
		userService.getUserById(1L);

		verify(userRepository, times(1)).findById(1L); // database was asked only once
	}

	@Test
	void updateChangesWhatTheCacheReturns() {
		UserEntity old = new UserEntity(1L, "Asha", "asha@example.com");
		when(userRepository.findById(1L)).thenReturn(Optional.of(old));
		when(userRepository.save(any(UserEntity.class))).thenAnswer(call -> call.getArgument(0));

		userService.getUserById(1L);
		userService.updateUser(1L, new UserEntity(null, "Asha K", "new@example.com"));

		assertThat(userService.getUserById(1L).getName()).isEqualTo("Asha K");
	}

	@Test
	void deleteRemovesUserFromCache() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(new UserEntity(1L, "Asha", "asha@example.com")));

		userService.getUserById(1L);
		assertThat(cacheManager.getCache("users").get(1L)).isNotNull();

		userService.deleteUser(1L);

		assertThat(cacheManager.getCache("users").get(1L)).isNull();
	}
}
