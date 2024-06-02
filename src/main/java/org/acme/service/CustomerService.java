package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.PathParam;
import org.acme.entity.Customer;
import org.acme.repository.CustomerRepo;

@ApplicationScoped
public class CustomerService {
    @Inject
    CustomerRepo customerRepo;

    @Transactional
    public void registerCustomer(Customer customer) {
        customerRepo.persist(customer);
    }

    @Transactional
    public Customer getCustomerById(@PathParam("id") Long id) {
        return customerRepo.findById(id);
    }
}
