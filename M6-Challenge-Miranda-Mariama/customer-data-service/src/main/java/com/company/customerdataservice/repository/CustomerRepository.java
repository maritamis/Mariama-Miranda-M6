package com.company.customerdataservice.repository;

import com.company.customerdataservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // The operations such as findById are automatically included due to the repository extending JpaRepository which includes the CRUD operations
    List<Customer> findByState(String state);
}


