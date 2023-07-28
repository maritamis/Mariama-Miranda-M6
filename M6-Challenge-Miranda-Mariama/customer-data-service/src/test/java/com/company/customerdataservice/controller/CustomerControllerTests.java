package com.company.customerdataservice.controller;

import com.company.customerdataservice.model.Customer;
import com.company.customerdataservice.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(CustomerController.class)
public class CustomerControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() throws Exception{
        repo.deleteAll();
    }

    //Test POST Customer
    @Test
    public void testPostNewCustomerShouldReturn201() throws Exception{
        Customer customer = new Customer();
        customer.setFirstName("Pli");
        customer.setLastName("Down");
        customer.setCity("Mai");
        customer.setState("NY");
        repo.save(customer);
        String customerJson = mapper.writeValueAsString(customer);
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    //Tests Put Customer
    @Test
    public void testPutCustomerShouldReturnNoContent() throws Exception{
        Customer customer = new Customer();
        customer.setFirstName("Pli");
        customer.setLastName("Down");
        customer.setCity("Mai");
        customer.setState("NY");
        customer.setId(1000);
        int id = customer.getId();

        String customerJson = mapper.writeValueAsString(customer);
        mockMvc.perform(put("/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    //Tests GET Customer By Id
    @Test
    public void testGetCustomerByIdShouldReturn200() throws Exception{
        Customer customer1 = new Customer();
        customer1.setFirstName("Pli");
        customer1.setLastName("Down");
        customer1.setCity("Mai");
        customer1.setState("NY");
        customer1.setId(200);
        int id = customer1.getId();
        customer1 = repo.save(customer1);
        mockMvc.perform(get("/customers/id/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // Tests Get by state
    @Test
    public void testGetByStateShouldReturn200() throws Exception{
        Customer customer = new Customer();
        customer.setFirstName("Mary");
        customer.setState("CA");
        repo.save(customer);

        String state = customer.getState();
        mockMvc.perform(get("/customers/state/{state}", state))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // Tests Delete Customer
    @Test
    public void testDeleteShouldReturnNoContent() throws Exception{
        Customer customer = new Customer();
        customer.setId(100);
        customer.setFirstName("Daon");
        customer.setLastName("Phul");
        customer.setState("NY");
        int id = customer.getId();

        mockMvc.perform(delete("/customers/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
