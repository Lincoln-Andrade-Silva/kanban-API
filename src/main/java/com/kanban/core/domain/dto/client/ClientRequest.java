package com.kanban.core.domain.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {
    private String name;
    private Long projectId;

    @Override
    public String toString() {
        return " ClientRequest = {" +
                ", name: " + name +
                ", projectId: " + projectId +
                '}';
    }
}