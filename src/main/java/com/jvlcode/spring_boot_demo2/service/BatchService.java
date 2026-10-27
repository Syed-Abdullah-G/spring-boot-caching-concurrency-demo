package com.jvlcode.spring_boot_demo2.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.jvlcode.spring_boot_demo2.exceptions.ResourceNotFoundException;
import com.jvlcode.spring_boot_demo2.repository.UserRepository;

import jakarta.annotation.PreDestroy;

@Service
public class BatchService {

	private final UserRepository userRepository;

	// A pool of 4 worker threads, created once and reused (we never create a thread per request).
	private final ExecutorService pool = Executors.newFixedThreadPool(4);

	// Remembers every batch by its id. A ConcurrentHashMap is safe to use from many threads.
	private final Map<String, BatchTask> tasks = new ConcurrentHashMap<>();

	public BatchService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// Gives each user to the pool and returns right away. The work continues in the background.
	public String start(List<Long> userIds) {
		BatchTask task = new BatchTask(userIds.size());
		tasks.put(task.getTaskId(), task);
		for (Long id : userIds) {
			pool.submit(() -> checkUser(task, id));
		}
		return task.getTaskId();
	}

	public BatchTask getTask(String taskId) {
		BatchTask task = tasks.get(taskId);
		if (task == null) {
			throw new ResourceNotFoundException("Task not found: " + taskId);
		}
		return task;
	}

	// Runs on a worker thread: checks one user and records the outcome.
	private void checkUser(BatchTask task, Long id) {
		String result;
		try {
			result = userRepository.findById(id)
					.map(user -> isBlank(user.getName()) || isBlank(user.getEmail())
							? "INVALID (name or email is empty)"
							: "OK")
					.orElse("NOT FOUND");
		} catch (Exception e) {
			result = "ERROR (" + e.getMessage() + ")";
		}
		task.addResult("User " + id + ": " + result + " [" + Thread.currentThread().getName() + "]");
	}

	private boolean isBlank(String text) {
		return text == null || text.isBlank();
	}

	// Stop the worker threads when the application stops.
	@PreDestroy
	public void shutdown() {
		pool.shutdown();
	}
}
