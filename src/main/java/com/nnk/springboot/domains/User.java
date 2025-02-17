package com.nnk.springboot.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "username is required")
    @Size(min = 1, message = "username cannot be empty")
    private String username;

    @NotNull(message = "password is required")
    @Size(min = 8, message = "password should be 8 character long or more")
    private String password;

    @NotNull(message = "fullname is required")
    @Size(min = 1, message = "fullname cannot be empty")
    private String fullname;

    @NotNull(message = "role is required")
    @Pattern(regexp = "user|admin", message = "role must be either 'User' or 'Admin'")
    private String role;
}
