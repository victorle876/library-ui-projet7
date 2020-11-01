package com.example.library.repository;

import com.example.library.model.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByMail(String mail);

    Optional<Utilisateur> findByUsername(String username);

    Optional<Utilisateur> findByUsernameAndPassword(String username, String password);

    Boolean existsByUsernameAndPassword(String username, String password);
    Boolean existsByUsername(String username);
    Boolean existsByPassword(String password);

    Boolean existsByMail(String mail);

}
