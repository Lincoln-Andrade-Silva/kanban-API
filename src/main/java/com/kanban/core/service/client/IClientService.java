package com.kanban.core.service.client;

import com.kanban.core.domain.dto.client.ClientRequest;
import com.kanban.core.domain.dto.client.ClientResponse;
import com.kanban.core.domain.dto.generic.DropdownResponse;
import com.kanban.util.exception.ApplicationBusinessException;
import com.kanban.util.response.DataListResponse;
import com.kanban.util.response.DataResponse;

public interface IClientService {
    DataListResponse<DropdownResponse> listForDropdown();

    DataResponse<ClientResponse> create(ClientRequest request) throws ApplicationBusinessException;

    DataResponse<ClientResponse> edit(Long clientId, ClientRequest request) throws ApplicationBusinessException;

    DataListResponse<ClientResponse> list(Integer page, Integer pageSize) throws ApplicationBusinessException;

    void deleteById(Long id) throws ApplicationBusinessException;
}
