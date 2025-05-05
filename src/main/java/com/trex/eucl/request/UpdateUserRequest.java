package com.trex.eucl.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateUserRequest {

    @NotBlank
    private String names;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "[0-9]{10,12}", message = "Your phone number must be one among these formats: 2507***, or 07*** ")
    private String phone;

    @Pattern(regexp = "[0-9]{16}", message = "Your National ID number must be 16 number digits")
    @NotBlank
    private String nationalId;

}
