package org.acme.route;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;
import org.acme.service.CustomerService;
import org.acme.entity.Customer;

@ApplicationScoped
@Api(value = "Customer API", produces = "application/json")
public class CustomerRoute extends RouteBuilder {

    @Inject
    CustomerService customerService;

    @Override
    public void configure() throws Exception {
        restConfiguration().contextPath("/api").port(8080);

        rest("/customers")
                .post().type(Customer.class).consumes("application/json").produces("application/json").to("direct:registerCustomer")
                .get("/{id}").produces("application/json").to("direct:getCustomerById");

        from("direct:registerCustomer")
                .setProperty("customerId", header("id"))
                .bean(customerService, "registerCustomer")
                .setBody(constant("Customer registered successfully"))
                .setHeader("Content-Type", constant("application/json"));

        from("direct:getCustomerById")
                .bean(customerService, "getCustomerById(${header.id})")
                .choice()
                .when(body().isNull())
                .setHeader("Content-Type", constant("application/json"))
                .setBody(constant("{ \"message\": \"Customer not found for order ID: ${header.id}\" }"))
                .setHeader("CamelHttpResponseCode", constant(404))
                .otherwise()
                .setHeader("Content-Type", constant("application/json"));
    }
}
