package com.trex.eucl.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MeterRequest {

    @Pattern(regexp = "[0-9]{6}", message = "Meter number must have 6 number digits")
    @NotBlank
    String meterNumber;

    @NotNull(message = "Id must not be null")
    UUID userId;

}
