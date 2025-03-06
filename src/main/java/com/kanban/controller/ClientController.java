package com.kanban.controller;

import com.kanban.core.domain.common.DomainReturnCode;
import com.kanban.core.domain.dto.client.ClientRequest;
import com.kanban.core.domain.dto.client.ClientResponse;
import com.kanban.core.domain.dto.generic.DropdownResponse;
import com.kanban.core.service.client.IClientService;
import com.kanban.util.exception.ApplicationBusinessException;
import com.kanban.util.response.DataListResponse;
import com.kanban.util.response.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "client")
@CrossOrigin(origins = "${api.access.control.allow.origin}")
@Tag(name = "Client Controller", description = "Endpoints of Client Controller")
public class ClientController {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private static final String STARTED = " - Started";
    private static final String FINISHED = " - Finished";

    private final IClientService service;

    @GetMapping()
    @Operation(summary = "List Clients", description = "List All Clients from DB")
    public DataListResponse<ClientResponse> list(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "pageSize") Integer pageSize
    ) throws ApplicationBusinessException {
        log.info("List method" + STARTED);
        log.debug("List method params, Page:{}, Page Size: {}", page, pageSize);

        DataListResponse<ClientResponse> response = service.list(page, pageSize);
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());

        log.info("List method" + FINISHED);
        return response;
    }

    @GetMapping("/dropdown")
    @Operation(summary = "List Clients for Dropdown", description = "List Clients for dropdown with id and description")
    public DataListResponse<DropdownResponse> listClientsForDropdown() {
        log.info("List Clients for Dropdown method started");

        DataListResponse<DropdownResponse> response = service.listForDropdown();

        log.info("List Clients for Dropdown method finished");
        return response;
    }

    @PostMapping
    @Operation(summary = "Create Client", description = "Create a new Client in DB")
    public DataResponse<ClientResponse> create(@RequestBody ClientRequest request) throws ApplicationBusinessException {
        log.info("Create method" + STARTED);
        log.debug("Create method params: {}", request);

        DataResponse<ClientResponse> response = service.create(request);
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());

        log.info("Create method" + FINISHED);
        return response;
    }

    @PutMapping("/{clientId}")
    @Operation(summary = "Edit Client", description = "Edit an existing Client in DB")
    public DataResponse<ClientResponse> edit(
            @PathVariable Long clientId,
            @RequestBody ClientRequest request) throws ApplicationBusinessException {

        log.info("Edit method" + STARTED);
        log.debug("Edit method params: clientId={}, request={}", clientId, request);

        DataResponse<ClientResponse> response = service.edit(clientId, request);
        response.setMessage(DomainReturnCode.SUCCESSFUL_OPERATION.getDesc());

        log.info("Edit method" + FINISHED);
        return response;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Client", description = "Delete a client by ID")
    public void delete(@PathVariable Long id) throws ApplicationBusinessException {
        log.info("Delete method - Started");
        log.debug("Delete method params: ID {}", id);

        service.deleteById(id);

        log.info("Delete method - Finished");
    }

}
