package com.kanban.core.domain.dto.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectStatusTest {

    @Test
    public void testFromDescription() {
        assertEquals(ProjectStatus.EM_ANDAMENTO, ProjectStatus.fromDescription("Em Andamento"));
        assertEquals(ProjectStatus.CANCELADO, ProjectStatus.fromDescription("Cancelado"));
        assertEquals(ProjectStatus.FINALIZADO, ProjectStatus.fromDescription("Finalizado"));
    }

    @Test
    public void testFromDescriptionCaseInsensitive() {
        assertEquals(ProjectStatus.EM_ANDAMENTO, ProjectStatus.fromDescription("em andamento"));
        assertEquals(ProjectStatus.CANCELADO, ProjectStatus.fromDescription("cancelado"));
        assertEquals(ProjectStatus.FINALIZADO, ProjectStatus.fromDescription("finalizado"));
    }

    @Test
    public void testFromDescriptionWithInvalidDescription() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ProjectStatus.fromDescription("Invalid");
        });
        assertEquals("Descrição inválida: Invalid", exception.getMessage());
    }

    @Test
    public void testGetKey() {
        assertEquals("1", ProjectStatus.EM_ANDAMENTO.getKey());
        assertEquals("2", ProjectStatus.CANCELADO.getKey());
        assertEquals("3", ProjectStatus.FINALIZADO.getKey());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Em Andamento", ProjectStatus.EM_ANDAMENTO.getDescription());
        assertEquals("Cancelado", ProjectStatus.CANCELADO.getDescription());
        assertEquals("Finalizado", ProjectStatus.FINALIZADO.getDescription());
    }
}