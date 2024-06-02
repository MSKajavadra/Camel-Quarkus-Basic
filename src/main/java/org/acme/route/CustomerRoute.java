package org.acme.route;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.apache.camel.builder.RouteBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
                .post().type(Customer.class).consumes("application/json").produces("application/json")
                .to("direct:registerCustomer")
                .get("/{id}").produces("application/json")
                .to("direct:getCustomerById");

        from("direct:registerCustomer")
                .setProperty("customerId", header("id"))
                .bean(customerService, "registerCustomer")
                .marshal().json()
                .setHeader("Content-Type", constant("application/json"))
                .routeId("RegisterCustomerRoute") // Add a route ID for better Swagger documentation
                .description("Register a new customer") // Add description for Swagger documentation
                .to("direct:registerCustomer"); // Add Swagger annotation for operation


        from("direct:getCustomerById")
                .bean(customerService, "getCustomerById(${header.id})")
                .marshal().json()
                .setHeader("Content-Type", constant("application/json"))
                .routeId("GetCustomerByIdRoute") // Add a route ID for better Swagger documentation
                .description("Get customer by ID") // Add description for Swagger documentation
                .to("direct:getCustomerById"); // Add Swagger annotation for operation
    }

    @ApiOperation(value = "Register a new customer", produces = "application/json")
    public void registerCustomer() {
        // This method is just for Swagger documentation purposes
    }

    @Path("/abc")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Get customer by ID", produces = "application/json")
    public void getCustomerById() {
        // This method is just for Swagger documentation purposes
    }
}
