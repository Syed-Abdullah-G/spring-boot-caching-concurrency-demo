package com.jvlcode.spring_boot_demo2.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.jvlcode.spring_boot_demo2.entity.UserEntity;
import com.jvlcode.spring_boot_demo2.repository.UserRepository;

class BatchServiceTest {

	@Test
	void batchCompletesAndReportsEachUser() throws InterruptedException {
		UserRepository repo = mock(UserRepository.class);
		when(repo.findById(1L)).thenReturn(Optional.of(new UserEntity(1L, "Asha", "asha@example.com")));
		when(repo.findById(2L)).thenReturn(Optional.of(new UserEntity(2L, "", "x@example.com")));
		when(repo.findById(3L)).thenReturn(Optional.empty());
		BatchService service = new BatchService(repo);

		try {
			String taskId = service.start(List.of(1L, 2L, 3L));

			// wait at most 5 seconds for the background threads to finish
			long deadline = System.currentTimeMillis() + 5000;
			while (!"COMPLETED".equals(service.getTask(taskId).getStatus()) && System.currentTimeMillis() < deadline) {
				Thread.sleep(20);
			}

			BatchTask task = service.getTask(taskId);
			assertThat(task.getStatus()).isEqualTo("COMPLETED");
			assertThat(task.getResults()).hasSize(3);
			assertThat(task.getResults()).anyMatch(r -> r.startsWith("User 1: OK"));
			assertThat(task.getResults()).anyMatch(r -> r.startsWith("User 2: INVALID"));
			assertThat(task.getResults()).anyMatch(r -> r.startsWith("User 3: NOT FOUND"));
		} finally {
			service.shutdown();
		}
	}
}
