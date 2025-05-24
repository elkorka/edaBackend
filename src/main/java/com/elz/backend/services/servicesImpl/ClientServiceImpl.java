package com.elz.backend.services.servicesImpl;

import com.elz.backend.Exceptions.ClientAlreadyExistsException;
import com.elz.backend.Exceptions.ClientNotFoundException;
import com.elz.backend.dto.ClientDto;
import com.elz.backend.entities.Client;
import com.elz.backend.mappers.ClientMapper;
import com.elz.backend.repository.ClientRepository;
import com.elz.backend.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Setter

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    @Override
    public ClientDto createClient(ClientDto clientDto) throws ClientAlreadyExistsException {


        Optional<ClientDto> clientDansleBD=clientRepository.findByEmail(clientDto.getEmail());
        if (clientDansleBD.isPresent()){
            throw new ClientAlreadyExistsException("Un client avec cet email existe déja ");
        }
        Client nouveauClient=clientMapper.mapToClientEntity(clientDto);
        Client clientSeved= clientRepository.save(nouveauClient);
        log.info("Nouveau client créer : {}",nouveauClient.getEmail());

        return clientMapper.mapToClientDto(clientSeved);
        //if (clientDansleBD.isEmpty()){
            // //log.info("Création d'un nouveau client : {}" , clientDto);
            //clientRepository.save(clientMapper.mapToClientEntity(clientDto));
           // // clientRepository.save(clientDto.mapToEntity(clientDto));
        //}
    }

    @Override
    public ClientDto getClientById(Long id) throws ClientNotFoundException {

        Client client = this.clientRepository.findById(id)
                .orElseThrow(()-> new ClientNotFoundException("Aucun client n'existe avec ce id"));
        return  clientMapper.mapToClientDto(client);

    }

//    @Override
//    public List<ClientDto> searchClient(String prenomOunom) {
//        List<ClientDto> clientList=clientRepository.searchClient(prenomOunom);
//        if (clientList.isEmpty()){
//            log.info("Aucun client trouvé");
//            return Collections.emptyList();
//        }
//        return clientList;
//    }

    @Override
    public List<ClientDto> getAllClient() {
        List<Client> clientList =this.clientRepository.findAll();
        if (clientList.isEmpty()){
            log.info("Aucun client trouvé ");
            return Collections.emptyList();
        }
        return clientMapper.mapToListClientDto(clientList);
        //return clientList.stream().map(ClientDto::mapToDto).collect(Collectors.toList());

    }

    @Override
    public ClientDto updateClient(Long id,ClientDto clientDto) throws ClientNotFoundException, ClientAlreadyExistsException {
        Client clientExistant= clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client non trouvé avec l'ID : " + id));

        // Mettre à jour uniquement les champs modifiables
        clientExistant.setNom(clientDto.getNom());
        clientExistant.setPrenom(clientDto.getPrenom());
        clientExistant.setAdresse(clientDto.getAdresse());
        clientExistant.setTelephone(clientDto.getTelephone());

        // Vérifier si l'email est modifié et unique

        if (! clientExistant.getEmail().equals(clientDto.getEmail())){
            Optional<ClientDto> clientAvecEmail=clientRepository.findByEmail(clientDto.getEmail());
            if (clientAvecEmail.isPresent()){
                throw new ClientAlreadyExistsException("Un client avec cet email existe déjà");
            }
            clientExistant.setEmail(clientDto.getEmail());
        }
        clientExistant.setUpdatedAt(LocalDateTime.now());
        log.info("Client mise à jour : {}",clientExistant.getEmail());
        return clientMapper.mapToClientDto(clientRepository.save(clientExistant));


        /*Optional<Client> clientDansBdd= clientRepository.findById(id);
        if (clientDansBdd.isPresent()){
            if (clientDansBdd.get().getIdClient()==clientDto.getIdClient()){
                clientDansBdd.get().setAdresse(clientDto.getAdresse());
                clientDansBdd.get().setNom(clientDto.getNom());
                clientDansBdd.get().setPrenom(clientDto.getPrenom());
                clientDansBdd.get().setEmail(clientDto.getEmail());
                clientDansBdd.get().setTelephone(clientDto.getTelephone());
                clientDansBdd.get().setUpdatedAt(LocalDateTime.now());
                clientRepository.save(clientDansBdd.get());

            }
        }
        */
    }

    @Override
    public void deleteClientById(Long id) throws ClientNotFoundException {
        log.info("Suppression Client ");
        Client client = this.clientRepository.findById(id)
                .orElseThrow(()-> new ClientNotFoundException("Aucun cllient n'existe avec ce id"));
        //if (client==null) throw new RessourceNotFoundException("Client not Found");
        log.info("Client supprimé avec succés  : {}",client.getEmail());
        this.clientRepository.deleteById(id);
    }


}
