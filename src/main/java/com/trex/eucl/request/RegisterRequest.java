package com.trex.eucl.request;

import com.trex.eucl.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

        @NotBlank
        String names;

        @Email
        @NotBlank
        String email;

        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[A-Za-z\\d\\W_]{8,}$",
                message = "Password must have at least 8 characters, one symbol, one number, and one uppercase letter.")
        String password;

        @NotBlank
        @Pattern(regexp = "[0-9]{10,12}", message = "Your phone number must be one among these formats: 2507***, or 07*** ")
        String phone;

        @NotBlank
        @Pattern(regexp = "[0-9]{16}", message = "Your National ID number must be 16 number digits")
        String nationalId;

        @NotBlank
        Roles role;

}