package com.crown.test.customer;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CustomerController.class})
@ExtendWith(SpringExtension.class)
public class CustomerControllerTest {
    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testCreate() throws Exception {
        BillingDetails billingDetails = new BillingDetails();
        billingDetails.setAccountNumber("0000000000-01");
        billingDetails.setTariff("Tariff");

        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setBillingDetails(billingDetails);
        customer.setId(123L);
        customer.setFirstName("Jane");
        when(this.customerService.create((Customer) any())).thenReturn(customer);

        BillingDetails billingDetails1 = new BillingDetails();
        billingDetails1.setAccountNumber("0000000000-01");
        billingDetails1.setTariff("Tariff");

        Customer customer1 = new Customer();
        customer1.setLastName("Doe");
        customer1.setEmail("jane.doe@example.org");
        customer1.setBillingDetails(billingDetails1);
        customer1.setId(123L);
        customer1.setFirstName("Jane");
        String content = (new ObjectMapper()).writeValueAsString(customer1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString(
                                "{\"payload\":{\"id\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"billingDetails"
                                        + "\":{\"accountNumber\":\"0000000000-01\",\"tariff\":\"Tariff\"}},\"status\":\"OK\",\"messages\":[{\"msg\":\"Customer"
                                        + " created successfully\",\"msgType\":\"SUCCESS\"}]}")));
    }

    @Test
    public void testFindAll() throws Exception {
        when(this.customerService.findAll()).thenReturn(new ArrayList<Customer>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/findAll");
        MockMvcBuilders.standaloneSetup(this.customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"payload\":[],\"status\":\"OK\",\"messages\":[]}")));
    }

    @Test
    public void testFindAll4() throws Exception {
        when(this.customerService.findAll()).thenReturn(new ArrayList<Customer>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/customer/findAll");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.customerController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("{\"payload\":[],\"status\":\"OK\",\"messages\":[]}")));
    }

    @Test
    public void testFindById() throws Exception {
        BillingDetails billingDetails = new BillingDetails();
        billingDetails.setAccountNumber("0902902902-01");
        billingDetails.setTariff("Tariff");

        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setBillingDetails(billingDetails);
        customer.setId(123L);
        customer.setFirstName("Jane");
        when(this.customerService.findById((Long) any())).thenReturn(customer);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/findById/{id}", 1L);
        MockMvcBuilders.standaloneSetup(this.customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString(
                                "{\"id\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"billingDetails\":{"
                                        + "\"accountNumber\":\"0902902902-01\",\"tariff\":\"Tariff\"}}")));
    }

    @Test
    public void testFindById2() throws Exception {
        BillingDetails billingDetails = new BillingDetails();
        billingDetails.setAccountNumber("0902902902-01");
        billingDetails.setTariff("Tariff");

        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setBillingDetails(billingDetails);
        customer.setId(123L);
        customer.setFirstName("Jane");
        when(this.customerService.findById((Long) any())).thenReturn(customer);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/findById/{id}", 1L);
        MockMvcBuilders.standaloneSetup(this.customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString(
                                "{\"id\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"billingDetails\":{"
                                        + "\"accountNumber\":\"0902902902-01\",\"tariff\":\"Tariff\"}}")));
    }
}

