package com.example1.library.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LivreDTO {
    private Long id;

    private String titre;

    private String auteur;

    private String categorie;

    private Date createdAt;

    private Date updatedAt;

    private List<ExemplaireDTO> exemplaires;

}
