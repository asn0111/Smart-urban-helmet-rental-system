package com.asn.smarthelmetproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "helmet")
public class Helmet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonBackReference
    private Location location;
}
