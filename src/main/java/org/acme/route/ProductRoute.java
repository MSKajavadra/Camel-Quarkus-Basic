package org.acme.route;

import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Product;
import org.acme.service.ProductService;
import org.apache.camel.builder.RouteBuilder;

import java.util.List;

@ApplicationScoped
public class ProductRoute extends RouteBuilder {

    @Inject
    ProductService productService;

    @Override
    public void configure() throws Exception {
        restConfiguration().contextPath("/api").port(8080);

        rest("/products")
                .get().to("direct:listProducts");

        from("direct:listProducts")
                .bean(productService, "getAllProducts")
                .setHeader("Content-Type", constant("application/json"));
    }
}
