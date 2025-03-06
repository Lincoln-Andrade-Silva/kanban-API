package com.kanban.core.domain.dto.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {
    private String name;
    private String status;
    private List<Long> clientIds;

    @Override
    public String toString() {
        return "ProjectRequest = {" +
                " name: " + name +
                ", status: " + status +
                ", clientIds: " + (clientIds != null ? Strings.join(clientIds.stream().map(String::valueOf).toList(), ',') : "[]") +
                " }";
    }
}
