package org.rk.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer extends BaseEntity {

    @ToString.Include
    private String name;

    @ToString.Include
    private LocalDate birthDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer")
    private Set<Order> orders; // 1 : n
}
