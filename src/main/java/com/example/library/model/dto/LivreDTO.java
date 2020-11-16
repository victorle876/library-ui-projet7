package com.example.library.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<ExemplaireDTO> exemplaires;

}
