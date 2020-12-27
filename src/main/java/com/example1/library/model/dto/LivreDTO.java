package com.example1.library.model.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
public class LivreDTO {
    private Long id;

    private String titre;

    private String auteur;

    private String categorie;

    @JsonProperty("createdAt")
    private Date createdAt;

    @JsonProperty("updatedAt")
    private Date updatedAt;

    @JsonManagedReference
    @ToString.Exclude
    private List<ExemplaireDTO> exemplaires;

}
