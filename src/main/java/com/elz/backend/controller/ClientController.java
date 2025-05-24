package com.elz.backend.controller;

import com.elz.backend.Exceptions.ClientAlreadyExistsException;
import com.elz.backend.Exceptions.ClientNotFoundException;
import com.elz.backend.dto.ClientDto;
import com.elz.backend.services.ClientService;
import com.elz.backend.services.servicesImpl.ClientServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1/client")
@Slf4j
@CrossOrigin("*")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto client) throws ClientAlreadyExistsException {

            clientService.createClient(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ClientDto getClientById(@PathVariable(name = "id") Long id ) throws ClientNotFoundException {
        return clientService.getClientById(id);
    }

//    @GetMapping("/search")
//    public List<ClientDto> searchClient(@RequestParam (name="prenonOuNom", defaultValue = "") String prenomOuNom){
//        return clientServiceImpl.searchClient("%" + prenomOuNom + "%");
//    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<List<ClientDto>> getAllClient(){
        return ResponseEntity.ok(this.clientService.getAllClient());
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void updateClient(@PathVariable(name = "id") Long id,@Valid @RequestBody ClientDto clientDto) throws ClientNotFoundException, ClientAlreadyExistsException {
        this.clientService.updateClient(id,clientDto);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteClientById(@PathVariable(name = "id") Long id) throws ClientNotFoundException {
        this.clientService.deleteClientById(id);
    }

}
