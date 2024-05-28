package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entity.Product;
import org.acme.repository.ProductRepo;

import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepo productRepository;

    @Transactional
    public List<Product> getAllProducts() {
        return productRepository.listAll();
    }
}
