package org.rk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor                      // zadny

public class Customer {
    private int id;
    private String name;
    private LocalDate birthDate;

}
