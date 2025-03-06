package com.kanban.core.domain.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientMessagesTest {

    @Test
    public void testConstants() {
        assertEquals("Name", ClientMessages.NAME);
        assertEquals("Cliente", ClientMessages.CLIENTE);
        assertEquals("Cliente não encontrado", ClientMessages.CLIENTE_NAO_ENCONTRADO);
        assertEquals("Cliente não encontrado para o ID: ", ClientMessages.CLIENTE_NAO_ENCONTRADO_ID);
        assertEquals("Não existem clientes cadastrados", ClientMessages.CLIENTE_NAO_CADASTRADO);
        assertEquals("Projeto não encontrado", ClientMessages.PROJETO_NAO_ENCONTRADO);
        assertEquals("Projeto não encontrado para o ID: ", ClientMessages.PROJETO_NAO_ENCONTRADO_ID);
    }
}