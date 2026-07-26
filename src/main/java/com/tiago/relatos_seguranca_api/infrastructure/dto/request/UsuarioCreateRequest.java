package com.tiago.relatos_seguranca_api.infrastructure.dto.request;

import com.tiago.relatos_seguranca_api.infrastructure.enums.ProfileEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UsuarioCreateRequest{ /*Usado apenas no POST.*/

        // @Schema(description = "User Name", example = "Tiago Oliveira")
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 3, max = 50, message = "Name must contain between 3 and 50 characters")
        @Pattern(
                regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$",
                message = "Name must contain only letters and spaces."
        )
        private String name;

        //@Schema(description = "User Email", example = "tiago@gmail.com")
        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid Email")
        @Size(min = 6, max = 50, message = "Email must contain between 3 and 50 characters")
        private String email;

        //@Schema(description = "User Password", example = "123456")
        @NotBlank(message = "Password cannot be empty")
        @Size(min = 6, max = 50)
        private String password;

        //@Schema(description = "User Profiles", example = "[\"ROLE_ADMIN\", \"ROLE_CUSTOMER\"]")
        private Set<ProfileEnum> profiles;

}