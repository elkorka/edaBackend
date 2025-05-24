package com.elz.backend.services;

import com.elz.backend.Exceptions.ClientAlreadyExistsException;
import com.elz.backend.Exceptions.ClientNotFoundException;
import com.elz.backend.dto.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto createClient(ClientDto clientDto) throws ClientAlreadyExistsException;
    ClientDto getClientById(Long id) throws ClientNotFoundException;
//    List<ClientDto> searchClient(String nom);
    List<ClientDto> getAllClient();
    ClientDto updateClient(Long id,ClientDto clientDto) throws ClientNotFoundException, ClientAlreadyExistsException;
    void deleteClientById(Long id) throws ClientNotFoundException;

}
