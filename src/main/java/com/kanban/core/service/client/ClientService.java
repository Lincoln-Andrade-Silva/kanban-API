package com.kanban.core.service.client;

import com.kanban.core.domain.common.ClientMessages;
import com.kanban.core.domain.dto.client.ClientRequest;
import com.kanban.core.domain.dto.client.ClientResponse;
import com.kanban.core.domain.dto.generic.DropdownResponse;
import com.kanban.core.domain.model.client.Client;
import com.kanban.core.domain.model.project.Project;
import com.kanban.core.domain.repository.ClientRepository;
import com.kanban.core.domain.repository.ProjectRepository;
import com.kanban.core.domain.validator.Validator;
import com.kanban.core.mapper.ClientMapper;
import com.kanban.util.exception.ApplicationBusinessException;
import com.kanban.util.response.DataListResponse;
import com.kanban.util.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientService {

    private final ClientRepository repository;
    private final ProjectRepository projectRepository;

    @Override
    public DataListResponse<ClientResponse> list(Integer pageNumber, Integer pageSize) throws ApplicationBusinessException {
        int size = pageSize == null ? 10 : pageSize;
        int page = (pageNumber == null || pageNumber < 1) ? 0 : pageNumber - 1;

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        Page<Client> clientPage = repository.findAll(pageable);

        Validator.validatePageNotEmpty(clientPage, ClientMessages.CLIENTE_NAO_CADASTRADO);

        return new DataListResponse<>(
                ClientMapper.entityToDTO(clientPage.getContent()),
                clientPage.getTotalPages(),
                clientPage.getTotalElements()
        );
    }

    @Override
    public DataListResponse<DropdownResponse> listForDropdown() {
        List<Client> clients = repository.findAll(Sort.by(Sort.Order.desc("id")));

        List<DropdownResponse> dropdownResponses = clients.stream()
                .map(client -> DropdownResponse.builder()
                        .id(client.getId())
                        .description(client.getName())
                        .build())
                .toList();

        return new DataListResponse<>(dropdownResponses, 0, (long) dropdownResponses.size());
    }

    @Override
    public DataResponse<ClientResponse> create(ClientRequest request) throws ApplicationBusinessException {
        Optional<Client> fromDB = repository.findByName(request.getName());
        Validator.validateIfExistWithSameField(fromDB, ClientMessages.NAME, ClientMessages.CLIENTE);

        Project projectFromDB = getProjectFromDB(request);

        Client client = ClientMapper.requestToEntity(request, projectFromDB);
        Client savedClient = repository.save(client);

        return new DataResponse<>(ClientMapper.entityToDTO(savedClient));
    }

    @Override
    public DataResponse<ClientResponse> edit(Long clientId, ClientRequest request) throws ApplicationBusinessException {
        Client clientFromDB = repository.findById(clientId)
                .orElseThrow(() -> new ApplicationBusinessException(ClientMessages.CLIENTE_NAO_ENCONTRADO, ClientMessages.CLIENTE_NAO_ENCONTRADO_ID + clientId));

        Optional<Client> fromDB = repository.findByNameAndDifferentId(request.getName(), clientId);
        Validator.validateIfExistWithSameField(fromDB, ClientMessages.NAME, ClientMessages.CLIENTE);

        Project projectFromDB = getProjectFromDB(request);

        ClientMapper.updateEntityFromRequest(clientFromDB, request, projectFromDB);

        Client updatedClient = repository.save(clientFromDB);

        return new DataResponse<>(ClientMapper.entityToDTO(updatedClient));
    }

    @Override
    public void deleteById(Long id) throws ApplicationBusinessException {
        Client client = repository.findById(id)
                .orElseThrow(() -> new ApplicationBusinessException(
                        404,
                        ClientMessages.CLIENTE_NAO_ENCONTRADO,
                        ClientMessages.CLIENTE_NAO_ENCONTRADO_ID + id,
                        ""
                ));

        repository.deleteById(client.getId());
    }

    private Project getProjectFromDB(ClientRequest request) throws ApplicationBusinessException {
        return request.getProjectId() != null && request.getProjectId() != 0
                ? projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new ApplicationBusinessException(ClientMessages.PROJETO_NAO_ENCONTRADO, ClientMessages.PROJETO_NAO_ENCONTRADO_ID + request.getProjectId()))
                : null;
    }
}