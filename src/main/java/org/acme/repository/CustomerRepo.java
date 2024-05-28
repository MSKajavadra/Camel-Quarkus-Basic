package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Customer;

@ApplicationScoped
public class CustomerRepo implements PanacheRepository<Customer> {
}
