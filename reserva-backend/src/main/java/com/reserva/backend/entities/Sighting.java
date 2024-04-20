package com.reserva.backend.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE sighting_type SET active = false WHERE id = ?")
public class Sighting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String scientificName;
    private Date createdAt;
    private String status;
    private double latitude;
    private double longitude;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "sightingType_id")
    private SightingType type;
    @ManyToOne
    @JoinColumn(name = "createdBy_id")
    private User createdBy;
    @ManyToOne
    @JoinColumn(name = "approvedBy_id")
    private User approvedBy;

    @OneToMany(mappedBy = "sighting", cascade = CascadeType.ALL)
    @Where(clause = "active = true")
    private List<Field> fields;

    @OneToMany(mappedBy = "sighting", cascade = CascadeType.ALL)
    private List<Image> images;
  
}
