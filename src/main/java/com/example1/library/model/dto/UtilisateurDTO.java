package com.example1.library.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UtilisateurDTO {

    private Long id;

    private String username;

    private String prenom;

    private String mail;

    private String password;

    private Integer age;

    private String statut;

    private Date createdAt;

    private Date updatedAt;

    private List<PretDTO> prets;

    private String token;

    private Set<RoleDTO> rolesDTO = new HashSet<>();
}

