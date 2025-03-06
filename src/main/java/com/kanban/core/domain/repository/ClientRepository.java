package com.kanban.core.domain.repository;

import com.kanban.core.domain.model.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE LOWER(c.name) = LOWER(:name)")
    Optional<Client> findByName(String name);

    @Query("SELECT c FROM Client c WHERE LOWER(c.name) = LOWER(:name) AND c.id <> :id")
    Optional<Client> findByNameAndDifferentId(String name, Long id);

    List<Client> findByProjectId(Long projectId);
}
