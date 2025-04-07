package ir.maktabsharif.finalproject.entity.Dto;

import ir.maktabsharif.finalproject.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDateTime;

@Builder
@Data
public class WebappUserRequestDto {
    @Size(min = 4 , max = 20 , message = "Username must be at least 4 characters long.")
    private String username;
    @Size(min = 8 , max = 20 , message = "password must be at least 8 characters long.")
    private String password;

    private String firstName;

    private String lastName;

    private String nationalCode;

    private LocalDateTime dob;

    private Role role;

}
