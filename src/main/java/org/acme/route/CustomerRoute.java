import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
                .description("Register a new customer"); // Add description for Swagger documentation

        from("direct:getCustomerById")
                .bean(customerService, "getCustomerById(${header.id})")
                .marshal().json()
                .setHeader("Content-Type", constant("application/json"))
                .routeId("GetCustomerByIdRoute") // Add a route ID for better Swagger documentation
                .description("Get customer by ID"); // Add description for Swagger documentation
    }
}
