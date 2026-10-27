package com.jvlcode.spring_boot_demo2.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

// Holds the progress of one batch. Several threads update it at the same time,
// so it uses thread-safe types (volatile, AtomicInteger, CopyOnWriteArrayList).
public class BatchTask {

	private final String taskId = UUID.randomUUID().toString();
	private volatile String status = "RUNNING";                     // RUNNING -> COMPLETED
	private final AtomicInteger remaining;                          // users still to be checked
	private final List<String> results = new CopyOnWriteArrayList<>();

	public BatchTask(int totalUsers) {
		this.remaining = new AtomicInteger(totalUsers);
	}

	// Called by a worker thread when it finishes one user.
	public void addResult(String result) {
		results.add(result);
		if (remaining.decrementAndGet() == 0) {
			status = "COMPLETED";
		}
	}

	public String getTaskId() { return taskId; }
	public String getStatus() { return status; }
	public List<String> getResults() { return results; }
}
