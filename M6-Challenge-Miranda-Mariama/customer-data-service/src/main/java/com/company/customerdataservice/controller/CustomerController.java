package com.company.customerdataservice.controller;

import com.company.customerdataservice.model.Customer;
import com.company.customerdataservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository repo;

    //POST to create a new customer
    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createNewCustomer(@RequestBody Customer customer){
        return repo.save(customer);
    }

    //PUT to update existing customer
    @PutMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@RequestBody Customer customer, @PathVariable int id){
        repo.save(customer);
    }

    //DELETE an existing customer
    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int id){
        repo.deleteById(id);
    }

    //GET a specific customer by id
    @GetMapping("/customers/id/{id}")
    public Customer getCustomerById(@PathVariable int id){
        Optional<Customer> returnVal = repo.findById(id);
        if (returnVal.isPresent()){
            return returnVal.get();
        }
        else {
            return null;
        }
    }

    //GET customer records for a specific state
    @GetMapping("/customers/state/{state}")
    public List<Customer> getCustomerByState(@PathVariable String state){
        return repo.findByState(state);
    }
}
