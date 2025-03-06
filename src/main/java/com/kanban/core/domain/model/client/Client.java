package com.kanban.core.domain.model.client;

import com.kanban.core.domain.model.project.Project;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_client", indexes = {@Index(name = "index_tb_client", columnList = "id, name")})
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @JoinColumn(name = "project_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Override
    public String toString() {
        return "ClientResponse = {" +
                "id: " + id +
                ", name: " + name +
                ", project: " + (project != null ? project.getName() : "No project") +
                '}';
    }
}