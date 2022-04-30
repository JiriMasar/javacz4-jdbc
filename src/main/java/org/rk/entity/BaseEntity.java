package org.rk.entity;

import jakarta.persistence.*;
import lombok.ToString;

@MappedSuperclass
public class BaseEntity {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false) // autoincrement
    private int id;

}
