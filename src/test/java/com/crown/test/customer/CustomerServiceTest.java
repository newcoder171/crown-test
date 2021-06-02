package com.crown.test.customer;

import com.crown.test.core.exceptions.CustomerAlreadyExistException;
import com.crown.test.core.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CustomerService.class})
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testCreate() {
        BillingDetails billingDetails = new BillingDetails();
        billingDetails.setAccountNumber("1234567890-01");
        billingDetails.setTariff("Tariff");

        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setBillingDetails(billingDetails);
        customer.setId(123L);
        customer.setFirstName("Jane");
        when(this.customerRepository.save((Customer) any())).thenReturn(customer);
        assertSame(customer, this.customerService.create(customer));
        verify(this.customerRepository).save((Customer) any());
    }

    @Test
    public void testCreate3() {
        BillingDetails billingDetails = new BillingDetails();
        billingDetails.setAccountNumber("Account Number");
        billingDetails.setTariff("Tariff");

        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setBillingDetails(billingDetails);
        customer.setId(123L);
        customer.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.<Customer>of(customer);
        when(this.customerRepository.findByEmail(anyString())).thenReturn(ofResult);
        assertThrows(CustomerAlreadyExistException.class, () -> this.customerService.create(customer));
        verify(this.customerRepository).findByEmail(anyString());
    }

    @Test
    public void testCreate4() {
        BillingDetails billingDetails = new BillingDetails();
        billingDetails.setAccountNumber("1111111111-01");
        billingDetails.setTariff("Tariff");

        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setBillingDetails(billingDetails);
        customer.setId(123L);
        customer.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.<Customer>of(customer);
        when(this.customerRepository.findByBillingDetailsAccountNumber(anyString())).thenReturn(ofResult);
        when(this.customerRepository.findByEmail(anyString())).thenReturn(Optional.<Customer>empty());

        BillingDetails billingDetails1 = new BillingDetails();
        billingDetails1.setAccountNumber("09012029201-00");
        billingDetails1.setTariff("Tariff");

        Customer customer1 = new Customer();
        customer1.setBillingDetails(billingDetails1);
        assertThrows(IllegalArgumentException.class, () -> this.customerService.create(customer1));
        verify(this.customerRepository).findByBillingDetailsAccountNumber(customer1.getBillingDetails().getAccountNumber());
        verify(this.customerRepository).findByEmail(customer1.getEmail());
    }

    @Test
    public void testCreate5() {
        BillingDetails billingDetails = new BillingDetails();
        billingDetails.setAccountNumber("Account Number");
        billingDetails.setTariff("Tariff");

        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setBillingDetails(billingDetails);
        customer.setId(123L);
        customer.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.<Customer>of(customer);
        when(this.customerRepository.findByBillingDetailsAccountNumber(anyString())).thenReturn(ofResult);
        when(this.customerRepository.findByEmail(anyString())).thenReturn(Optional.<Customer>empty());

        BillingDetails billingDetails1 = new BillingDetails();
        billingDetails1.setAccountNumber("-01");
        billingDetails1.setTariff("Tariff");

        Customer customer1 = new Customer();
        customer1.setBillingDetails(billingDetails1);
        assertThrows(CustomerAlreadyExistException.class, () -> this.customerService.create(customer1));
        verify(this.customerRepository).findByBillingDetailsAccountNumber(customer1.getBillingDetails().getAccountNumber());
        verify(this.customerRepository).findByEmail(customer1.getEmail());
    }

    @Test
    public void testCreate6() {
        BillingDetails billingDetails = new BillingDetails();
        billingDetails.setAccountNumber("Account Number");
        billingDetails.setTariff("Tariff");

        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setBillingDetails(billingDetails);
        customer.setId(123L);
        customer.setFirstName("Jane");
        when(this.customerRepository.save((Customer) any())).thenReturn(customer);
        when(this.customerRepository.findByBillingDetailsAccountNumber(anyString())).thenReturn(Optional.<Customer>empty());
        when(this.customerRepository.findByEmail(anyString())).thenReturn(Optional.<Customer>empty());

        BillingDetails billingDetails1 = new BillingDetails();
        billingDetails1.setAccountNumber("-01");
        billingDetails1.setTariff("Tariff");

        Customer customer1 = new Customer();
        customer1.setBillingDetails(billingDetails1);
        assertSame(customer, this.customerService.create(customer1));
        verify(this.customerRepository).findByBillingDetailsAccountNumber(customer1.getBillingDetails().getAccountNumber());
        verify(this.customerRepository).save((Customer) customer1);
        verify(this.customerRepository).findByEmail(customer1.getEmail());
    }

    @Test
    public void testFindAll() {
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        when(this.customerRepository.findAll()).thenReturn(customerList);
        List<Customer> actualFindAllResult = this.customerService.findAll();
        assertSame(customerList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(this.customerRepository).findAll();
    }

    @Test
    public void testFindAll2() {
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        when(this.customerRepository.findAll()).thenReturn(customerList);
        List<Customer> actualFindAllResult = this.customerService.findAll();
        assertSame(customerList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(this.customerRepository).findAll();
    }

    @Test
    public void testFindById() {
        BillingDetails billingDetails = new BillingDetails();
        billingDetails.setAccountNumber("Account Number");
        billingDetails.setTariff("Tariff");

        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setBillingDetails(billingDetails);
        customer.setId(123L);
        customer.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.<Customer>of(customer);
        when(this.customerRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(customer, this.customerService.findById(123L));
        verify(this.customerRepository).findById((Long) any());
    }

    @Test
    public void testFindById2() {
        when(this.customerRepository.findById((Long) any())).thenReturn(Optional.<Customer>empty());
        assertThrows(CustomerNotFoundException.class, () -> this.customerService.findById(123L));
        verify(this.customerRepository).findById((Long) any());
    }

    @Test
    public void testFindById3() {
        BillingDetails billingDetails = new BillingDetails();
        billingDetails.setAccountNumber("Account Number");
        billingDetails.setTariff("Tariff");

        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setBillingDetails(billingDetails);
        customer.setId(123L);
        customer.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.<Customer>of(customer);
        when(this.customerRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(customer, this.customerService.findById(123L));
        verify(this.customerRepository).findById((Long) any());
    }

    @Test
    public void testFindById4() {
        when(this.customerRepository.findById((Long) any())).thenReturn(Optional.<Customer>empty());
        assertThrows(CustomerNotFoundException.class, () -> this.customerService.findById(123L));
        verify(this.customerRepository).findById((Long) any());
    }
}
