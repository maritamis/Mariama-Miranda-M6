package com.company.customerdataservice.repository;

import com.company.customerdataservice.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class CustomerRepositoryTests {

    @Autowired
    CustomerRepository repo;

    @BeforeEach
    public void setUp() throws Exception{
        repo.deleteAll();
    }

    @Test
    public void addCustomer(){
        // Arrange
        Customer customer = new Customer();
        customer.setFirstName("Poppy");
        customer.setLastName("Seed");
        customer.setAddress1("123 Main St.");
        customer.setCity("SF");
        customer.setState("CA");
        customer.setPostalCode("99999");
        customer.setCompany("Netflix");
        customer.setEmail("poppyseed@netflix.com");
        customer.setPhone("888-212-2121");

        customer = repo.save(customer);
        //Assert
        Optional<Customer> customer1 = repo.findById(customer.getId());

        assertEquals(customer1.get(),customer);
    }

    @Test
    public void getAllCustomers(){
        //Arrange 2 customers
        Customer customer = new Customer();
        customer.setFirstName("Poppy");
        customer.setLastName("Seed");
        customer.setAddress1("123 Main St.");
        customer.setCity("SF");
        customer.setState("CA");
        customer.setPostalCode("99999");
        customer.setCompany("Netflix");
        customer.setEmail("poppyseed@netflix.com");
        customer.setPhone("888-212-2121");

        repo.save(customer);

        Customer customer2 = new Customer();
        customer2.setFirstName("Pretty");
        customer2.setLastName("Being");
        customer2.setAddress1("259 Blooming St");
        customer2.setCity("SF");
        customer2.setState("CA");
        customer2.setPostalCode("99999");
        customer2.setCompany("OMG");
        customer2.setEmail("prettybeing@omg.com");
        customer2.setPhone("189-232-2121");

        repo.save(customer2);

        List<Customer> customerList = repo.findAll();
        //asserts that the customerList is the correct size
        assertEquals(2, customerList.size());
    }


    @Test
    public void updateCustomer(){
        //Arrange
        Customer customer = new Customer();
        customer.setFirstName("Bob");
        customer.setLastName("Marley");
        customer.setAddress1("266 Peace St");
        customer.setCity("SF");
        customer.setState("CA");
        customer.setPostalCode("99999");
        customer.setCompany("Music");
        customer.setEmail("musicislife@bobmarley.com");
        customer.setPhone("808-212-2121");

        repo.save(customer);

        customer.setCity("Seattle");
        customer.setState("WA");

        // saves the updated portion
        repo.save(customer);

        Optional<Customer> customer1 = repo.findById(customer.getId());
        assertEquals(customer1.get(), customer);
    }

    @Test
    public void deleteCustomerTest(){
        //Arrange
        Customer customer = new Customer();
        customer.setFirstName("Peace");
        customer.setLastName("Love");
        customer.setAddress1("2690 Peace St");
        customer.setCity("SF");
        customer.setState("CA");
        customer.setPostalCode("99999");
        customer.setCompany("Music");
        customer.setEmail("okay@bobmarley.com");
        customer.setPhone("808-212-2121");

        repo.save(customer);

        repo.deleteById(customer.getId());
        Optional<Customer> customer1 = repo.findById(customer.getId());

        assertFalse(customer1.isPresent());
    }


    @Test
    public void findCustomersByStateTest(){
        //adding customer in WA
        Customer customer = new Customer();
        customer.setFirstName("Peace");
        customer.setLastName("Love");
        customer.setAddress1("2690 Peace St");
        customer.setCity("Seattle");
        customer.setState("WA");
        customer.setPostalCode("99999");
        customer.setCompany("Music");
        customer.setEmail("okay@bobmarley.com");
        customer.setPhone("808-212-2121");
        repo.save(customer);

        // adding customer in CA
        Customer customer2 = new Customer();
        customer2.setFirstName("Listen");
        customer2.setLastName("There");
        customer2.setAddress1("26 North Peace St");
        customer2.setCity("SF");
        customer2.setState("CA");
        customer2.setPostalCode("99999");
        customer2.setCompany("Amz");
        customer2.setEmail("listening@sbcglobal.net");
        customer2.setPhone("808-090-2121");
        repo.save(customer2);


        // adding another customer in WA
        Customer customer3 = new Customer();
        customer3.setFirstName("Pleasant");
        customer3.setLastName("Grace");
        customer3.setAddress1("2690 Peace St");
        customer3.setCity("Seattle");
        customer3.setState("WA");
        customer3.setPostalCode("99199");
        customer3.setCompany("Zynga");
        customer3.setEmail("pleasantry@aol.com");
        customer3.setPhone("808-274-2121");
        repo.save(customer3);

        List<Customer> stateList = repo.findByState("WA");
        assertEquals(2, stateList.size());
    }
}
