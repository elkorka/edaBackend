package com.elz.backend.repository;

import com.elz.backend.dto.ClientDto;
import com.elz.backend.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<ClientDto> findByEmail(String email);

//    @Query("select * form client c where c.nom like :kw or c.prenom like :kw")
//    List<ClientDto> searchClient(@Param("kw") String nom);
}
