package com.example.library.model.dto;

import com.example.library.model.entity.Pret;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ExemplaireDTO {

    private Long id;

    private String collection;

    private String editeur ;

    private String description;

    private String isbn;

    private Date createdAt;

    private Date updatedAt;

    private Integer nombre;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private LivreDTO livre;

    private Date dateParution;

    private Pret pret;

}
