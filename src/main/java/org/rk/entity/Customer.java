package org.rk.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // autoincrement
    @Column(name = "id") // jmeno sloupce
    private int id;
    @Column
    private String name;
    @Column
    private LocalDate birthDate;


}
