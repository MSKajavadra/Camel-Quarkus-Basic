package org.acme.route;

import jakarta.inject.Inject;
import org.acme.entity.Customer;
import org.acme.service.CustomerService;
import org.apache.camel.builder.RouteBuilder;

public class CustomerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:registerCustomer")
                .to("bean:customerService?method=registerCustomer")
                .log("Customer registered successfully");
    }
}
