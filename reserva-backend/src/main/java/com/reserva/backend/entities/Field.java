package com.reserva.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String title;
    private boolean active;
    
    @Column(length = 1000)
    private String description;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sighting_id")
    private Sighting sighting;
    
}
