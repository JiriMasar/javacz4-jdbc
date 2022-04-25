package org.rk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // autoincrement
    private int id;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
