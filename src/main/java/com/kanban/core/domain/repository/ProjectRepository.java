package com.kanban.core.domain.repository;

import com.kanban.core.domain.model.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
