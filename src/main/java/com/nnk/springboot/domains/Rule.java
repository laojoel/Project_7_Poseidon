package com.nnk.springboot.domains;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "rule")
public class Rule {
    @Id
    private Long id;

    // TODO: Map columns in data table RULENAME with corresponding java fields
}
