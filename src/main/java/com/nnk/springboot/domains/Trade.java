package com.nnk.springboot.domains;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "trade")
public class Trade {
    @Id
    private Long id;

    // TODO: Map columns in data table TRADE with corresponding java fields
}
