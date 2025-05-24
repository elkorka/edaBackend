package com.elz.backend.mappers;

import com.elz.backend.dto.ClientDto;
import com.elz.backend.entities.Client;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto mapToClientDto(Client client);
    Client mapToClientEntity(ClientDto clientDto);

    List<ClientDto> mapToListClientDto (List<Client> clients);
    List<Client> mapToListeClientEntity (List<ClientDto> clientDtos);
}
