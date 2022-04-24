package org.rk.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // autoincrement
    @Column(name = "id") // jmeno sloupce
    private int id;

    @ManyToOne
    @JoinColumn(name="id", nullable=true) // true je implicitní, proto šedé // negeneruje se sloupec pouze vazba
    private Customer customer; // n : 1

    @OneToMany(mappedBy = "order")
    private List<Item> itemList; // 1 : n

}
