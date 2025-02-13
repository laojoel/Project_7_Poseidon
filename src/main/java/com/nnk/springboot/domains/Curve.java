package com.nnk.springboot.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "curve")
public class Curve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Term must be greater to 0")
    @Min(value = 1, message = "Value must be greater to 0")
    private double term;

    @NotNull(message = "Value must be greater to 0")
    @Min(value = 1, message = "Value must be greater to 0")
    private double value;
}
