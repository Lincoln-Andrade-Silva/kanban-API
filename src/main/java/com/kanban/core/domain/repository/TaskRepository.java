package com.kanban.core.domain.repository;

import com.kanban.core.domain.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Integer projectId);
}
