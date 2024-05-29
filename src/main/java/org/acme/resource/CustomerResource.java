package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Customer;
import org.acme.service.CustomerService;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    @Inject
    CustomerService customerService;

    @POST
    @Transactional
    public Response registerCustomer(Customer customer) {
        customerService.registerCustomer(customer);
        return Response.ok().entity(customer).build();
    }
}
