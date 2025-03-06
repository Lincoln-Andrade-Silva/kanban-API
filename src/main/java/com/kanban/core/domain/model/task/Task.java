package com.kanban.core.domain.model.task;

import com.kanban.core.domain.dto.task.TaskStatus;
import com.kanban.core.domain.model.client.Client;
import com.kanban.core.domain.model.project.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_task", indexes = {@Index(name = "index_tb_task", columnList = "id, name")})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Override
    public String toString() {
        return "Task = {" +
                "id: " + id +
                ", name: " + name +
                ", status: " + status.getDescription() +
                ", project: " + project.getName() +
                ", client: " + (client != null ? client.getName() : "No client") +
                '}';
    }
}