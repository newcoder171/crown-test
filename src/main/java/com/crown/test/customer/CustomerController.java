package com.crown.test.customer;


import com.crown.test.core.data.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Customer customer) {
        return ResponseEntity.ok(Response.of(customerService.create(customer)).success("Customer created successfully"));
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(Response.of(customerService.findAll()));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        if(null==id || id.equals(0L)) {
            throw new IllegalArgumentException("Customer Id is not valid");
        }
        return ResponseEntity.ok(customerService.findById(id));
    }
}
