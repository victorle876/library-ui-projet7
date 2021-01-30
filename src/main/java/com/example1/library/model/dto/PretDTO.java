package com.example1.library.model.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class PretDTO {

    private int id;

    private Boolean emprunte;

    private Boolean prolonge;

    private Boolean retourne;

    private Boolean disponible;

    private Integer nombreProlonge;

    private Date createdAt;

    private Date updatedAt;

  //  @JsonIgnore
    @JsonBackReference
    private UtilisateurDTO utilisateur;

    private ExemplaireDTO exemplaire;

}
