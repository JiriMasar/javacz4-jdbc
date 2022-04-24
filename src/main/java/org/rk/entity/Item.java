package org.rk.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // autoincrement
    @Column(name = "id") // jmeno sloupce
    private int id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private CategoryEnum category;

    @ManyToOne
    @JoinColumn(name="id", nullable=true)
    private Order order;
}
