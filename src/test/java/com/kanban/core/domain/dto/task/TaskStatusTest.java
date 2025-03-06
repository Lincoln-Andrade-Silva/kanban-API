package com.kanban.core.domain.dto.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskStatusTest {

    @Test
    public void testFromDescriptionValid() {
        TaskStatus status = TaskStatus.fromDescription("Novo");
        assertEquals(TaskStatus.NOVO, status);

        status = TaskStatus.fromDescription("Em Progresso");
        assertEquals(TaskStatus.EM_PROGRESSO, status);

        status = TaskStatus.fromDescription("Em Teste");
        assertEquals(TaskStatus.EM_TESTE, status);

        status = TaskStatus.fromDescription("Finalizado");
        assertEquals(TaskStatus.FINALIZADO, status);
    }

    @Test
    public void testFromDescriptionInvalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            TaskStatus.fromDescription("Invalid Status");
        });
        assertEquals("Descrição inválida: Invalid Status", exception.getMessage());
    }

    @Test
    public void testGetKey() {
        assertEquals("1", TaskStatus.NOVO.getKey());
        assertEquals("2", TaskStatus.EM_PROGRESSO.getKey());
        assertEquals("3", TaskStatus.EM_TESTE.getKey());
        assertEquals("4", TaskStatus.FINALIZADO.getKey());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Novo", TaskStatus.NOVO.getDescription());
        assertEquals("Em Progresso", TaskStatus.EM_PROGRESSO.getDescription());
        assertEquals("Em Teste", TaskStatus.EM_TESTE.getDescription());
        assertEquals("Finalizado", TaskStatus.FINALIZADO.getDescription());
    }
}