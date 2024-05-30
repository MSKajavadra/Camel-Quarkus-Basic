package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Customer;
import org.acme.repository.CustomerRepo;

@ApplicationScoped
@Named("customerService")
public class CustomerService {
    @Inject
    CustomerRepo customerRepo;

    @Transactional
    public void registerCustomer(Customer customer) {
        customerRepo.persist(customer);
    }

    @Transactional
    public Customer getCustomerById(long id) {
        return customerRepo.find("id",id).firstResult();
    }
}
