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
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Moody's Rating is required")
    @Size(min = 1, message = "Moody's Rating cannot be empty")
    private String moodysRating;

    @NotNull(message = "Send P Rating is required")
    @Size(min = 1, message = "Send P Rating cannot be empty")
    private String sandPRating;

    @NotNull(message = "Fitch Rating is required")
    @Size(min = 1, message = "Fitch Rating cannot be empty")
    private String fitchRating;

    @NotNull(message = "orderNumber must be greater or equal to 0")
    @Min(value = 0, message = "orderNumber must be greater or equal to 0")
    private int orderNumber;
}
