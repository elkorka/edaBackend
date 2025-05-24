package com.elz.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ClientDto {
    private int idClient;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 1, max = 50, message = "Le nom doit contenir entre 1 et 50 caractères")
    private String nom;

    @NotBlank(message = "Le prenom est obligatoire")
    @Size(min = 1, max = 100, message = "Le nom doit contenir entre 1 et 100 caractères")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    private String telephone;
    private String adresse;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

   /* public ClientDto mapToDto(Client client){
        if (client==null){
            return null;
            //throw exception
        }
        ClientDto c =new ClientDto();
        c.setIdClient(client.getIdClient());
        c.setNom(client.getNom());
        c.setPrenom(client.getPrenom());
        c.setAdresse(client.getAdresse());
        c.setEmail(client.getEmail());
        c.setTelephone(client.getTelephone());
        c.setUpdatedAt(client.getUpdatedAt());
        c.setCreatedAt(client.getCreatedAt());

        return c;
    }

    public Client mapToEntity(ClientDto clientDto){
        Client c=new Client();
        if (clientDto==null){
            return null;
            //throw exception
        }
        Client ce =new Client();
        ce.setNom(clientDto.getNom());
        ce.setPrenom(clientDto.getPrenom());
        ce.setAdresse(clientDto.getAdresse());
        ce.setEmail(clientDto.getEmail());
        ce.setTelephone(clientDto.getTelephone());
        ce.setUpdatedAt(clientDto.getUpdatedAt());
        ce.setCreatedAt(clientDto.getCreatedAt());

        return ce;
    }
*/
}
