package com.example.library.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PretDTO {

    private Long id;

    private Boolean emprunte;

    private Boolean prolonge;

    private Boolean retourne;

    private Integer nombreProlonge;

    private Date createdAt;

    private Date updatedAt;

    private UtilisateurDTO utilisateur;
    private ExemplaireDTO exemplaire;

}
