package org.rk.entity;

import lombok.Data;

import java.time.LocalDate;

@Data

public class Customer {
    private int id;
    private String name;
    private LocalDate birthDate;

}
