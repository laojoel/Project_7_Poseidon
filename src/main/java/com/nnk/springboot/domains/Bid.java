package com.nnk.springboot.domains;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

@Setter
@Getter
@Entity
@Table(name = "bids")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Account is required")
    @Size(min = 1, message = "Account cannot be empty")
    private String account;

    @Valid
    @NotNull(message = "Type is required")
    @Size(min = 3, message = "Type cannot be empty")
    private String type;

    @NotNull(message = "Quantity must be greater to 0")
    @Min(value = 1, message = "Quantity must be greater to 0")
    private double quantity;
}
