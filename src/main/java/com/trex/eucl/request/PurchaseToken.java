package com.trex.eucl.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseToken {

    @NotBlank
    @Pattern(regexp = "[0-9]{6}", message = "The meter number must be 6 number digits")
    private String meterNumber;

    @NotNull(message = "Please provide an amount")
    @Min(value = 100, message = "Amount must be at least 100")
    private Integer amount;

}
