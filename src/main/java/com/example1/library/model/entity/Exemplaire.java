package com.example1.library.model.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Data
@Table(name = "Exemplaire")
public class Exemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String collection;

    @NotBlank
    private String editeur ;

    @NotBlank
    private String description;

    @NotBlank
    private String isbn;

    private Integer nombre;

    @CreatedDate
    @Column(name = "createdAt")
    private Date createdAt;


    @LastModifiedDate
    @Column(name = "updatedAt")
    private Date updatedAt;

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
        if (this.updatedAt == null) updatedAt = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
    }

    @ManyToOne
    @JoinColumn(name="livre_id", referencedColumnName = "id")
    @JsonBackReference
    private Livre livre;

    private Date dateParution;

    @OneToOne
    @JoinColumn(name = "pret_id", referencedColumnName = "id")
    private Pret pret;

}

