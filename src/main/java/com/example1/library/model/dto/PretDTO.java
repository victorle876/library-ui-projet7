package com.example1.library.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class PretDTO {

    private int id;

    private Boolean emprunte;

    private Boolean prolonge;

    private Boolean retourne;

    private Integer nombreProlonge;

    private Date createdAt;

    private Date updatedAt;

    @JsonIgnore
    private UtilisateurDTO utilisateur;

    private ExemplaireDTO exemplaire;

}
