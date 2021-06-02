package com.crown.test.customer;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue()
    private Long id;

    private String firstName;

    private String lastName;

    @Email(message = "Please provide a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    @Embedded
    @Valid
    private BillingDetails billingDetails;

}
