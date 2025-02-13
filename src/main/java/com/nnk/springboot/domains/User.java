package com.nnk.springboot.domains;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @NotEmpty(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;
}
