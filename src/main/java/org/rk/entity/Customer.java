package org.rk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer implements Serializable {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false) // autoincrement
    private int id;

    @ToString.Include
    private String name;

    @ToString.Include
    private LocalDate birthDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer")
    private Set<Order> orders; // 1 : n
}
