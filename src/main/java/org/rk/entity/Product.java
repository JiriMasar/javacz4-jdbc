package org.rk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends BaseEntity {
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
