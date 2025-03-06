package com.kanban.core.domain.model.project;

import com.kanban.core.domain.dto.project.ProjectStatus;
import com.kanban.core.domain.model.client.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_project", indexes = {@Index(name = "index_tb_project", columnList = "id, name")})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatus status;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Client> clients;

    @Override
    public String toString() {
        return "ProjectResponse = {" +
                "id: " + id +
                ", name: " + name +
                ", status: " + status.getDescription() +
                ", clients: " + (clients != null ? String.join(", ", clients.stream().map(Client::getName).toList()) : "No clients") +
                '}';
    }

}