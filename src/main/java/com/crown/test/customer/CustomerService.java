package com.crown.test.customer;

import com.crown.test.core.exceptions.CustomerAlreadyExistException;
import com.crown.test.core.exceptions.CustomerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer customer) {
        checkEmail(customer);
        checkAccountNo(customer);
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
    }

    private void checkEmail(Customer customer) {
        Optional<Customer> found =  customerRepository.findByEmail(customer.getEmail());
        if(found.isPresent())
            throw new CustomerAlreadyExistException("A user with email " + customer.getEmail() + " already exists");

    }

    private void checkAccountNo(Customer customer) {
        Optional<Customer> found = customerRepository.findByBillingDetailsAccountNumber(customer.getBillingDetails().getAccountNumber());
        validateAccountNumber(customer.getBillingDetails().getAccountNumber());
        if (found.isPresent())
            throw new CustomerAlreadyExistException("A user with account number " + customer.getBillingDetails().getAccountNumber() + " already exists");
    }

    private void validateAccountNumber(String accountNumber) {
        if (!accountNumber.endsWith("-01"))
            throw new IllegalArgumentException("Account Number must end with -01");
    }


}
