package com.trex.eucl.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseToken {

    private String meterNumber;
    private Integer amount;


}
