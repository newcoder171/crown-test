package com.crown.test.customer;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Embeddable
@Data
public class BillingDetails {

    @NotNull
    @Column(unique = true, length = 13)
    @Size(min = 13, max = 13, message = "Account Number must be 10 characters")
//    @Pattern(regexp = "-01$", message = "Account Number must end with -01")
    private String accountNumber;

    private String tariff;
}
