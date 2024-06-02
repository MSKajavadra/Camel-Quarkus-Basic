package org.acme.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.CustomerOrder;

@ApplicationScoped
public class OrderRepo implements PanacheRepository<CustomerOrder> {
}