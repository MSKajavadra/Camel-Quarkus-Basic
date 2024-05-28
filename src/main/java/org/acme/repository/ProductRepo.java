package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Product;

@ApplicationScoped
public class ProductRepo implements PanacheRepository<Product> {
}
