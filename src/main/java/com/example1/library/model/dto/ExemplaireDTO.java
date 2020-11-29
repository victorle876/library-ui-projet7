package com.example1.library.model.dto;

import com.example1.library.model.entity.Pret;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
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

    private LivreDTO livre;

    private Date dateParution;

    private Pret pret;

}
