package com.nnk.springboot.domains;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "rule")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is required")
    @Size(min = 1, message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Description is required")
    @Size(min = 3, message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Json is required")
    @Size(min = 3, message = "Json cannot be empty")
    private String json;

    @NotNull(message = "Template is required")
    @Size(min = 3, message = "Template cannot be empty")
    private String template;

    @Column(name = "sqlstr")
    @NotNull(message = "SQL is required")
    @Size(min = 3, message = "SQL cannot be empty")
    private String sqlStr;

    @Column(name = "sqlpart")
    @NotNull(message = "SQL Part is required")
    @Size(min = 3, message = "SQL Part cannot be empty")
    private String sqlPart;
}
