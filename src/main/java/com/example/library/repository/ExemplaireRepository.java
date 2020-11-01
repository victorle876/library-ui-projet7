package com.example.library.repository;


import com.example.library.model.entity.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Long> {

    @Query("select e from Exemplaire e inner join Livre l on e.livre.id = l.id where l.titre like %:chaineRecherche%")
    List<Exemplaire> findByLivre (String chaineRecherche);

    @Query("select e from Exemplaire e inner join Livre l on e.livre.id = l.id where l.auteur like %:chaineRecherche%")
    List<Exemplaire> findByLivre2 (String chaineRecherche);
}
