package com.example1.library.model.entity;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table(name = "Livres")
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String titre;

    @NotBlank
    private String auteur;

    @NotBlank
    private String categorie;

    @JsonProperty("createdAt")
    @CreatedDate
    @Column(name = "createdAt")
    private Date createdAt;

    @JsonProperty("updatedAt")
    @LastModifiedDate
    @Column(name = "updatedAt")
    private Date updatedAt;

    //private Date dateParution;

    @OneToMany(mappedBy="livre")
    @ToString.Exclude
    @JsonManagedReference
    private List<Exemplaire> exemplaires;

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
        if (this.updatedAt == null) updatedAt = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
    }

}

