package org.rk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // autoincrement
    private int id;

    @Column
    private String name;

    @Column()
    private BigDecimal price;

    @Column
    private CategoryEnum category;


}
