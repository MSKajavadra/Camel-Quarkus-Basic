import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.CustomerOrder;
import org.acme.service.OrderService;
import org.apache.camel.builder.RouteBuilder;

import java.util.List;

@ApplicationScoped
@Api(value = "Order API", produces = "application/json")
public class OrderRoute extends RouteBuilder {

    @Inject
    OrderService orderService;

    // Jackson ObjectMapper for JSON marshalling
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure() throws Exception {
        restConfiguration().contextPath("/api").port(8080);

        rest("/orders")
                .post().type(CustomerOrder.class)
                .to("direct:placeOrder")
                .get("/{customerId}")
                .to("direct:getOrderHistory");

        from("direct:placeOrder")
                .bean(orderService, "placeOrder")
                .marshal().json(ObjectMapper.class) // Marshalling to JSON using Jackson
                .setHeader("Content-Type", constant("application/json"))
                .routeId("PlaceOrderRoute")
                .description("Place a new order");

        from("direct:getOrderHistory")
                .bean(orderService, "getOrderHistory(${header.customerId})")
                .process(exchange -> {
                    List<CustomerOrder> orderHistory = exchange.getIn().getBody(List.class);
                    exchange.getIn().setBody(orderHistory); // Set body back to the list
                })
                .marshal().json(ObjectMapper.class) // Marshalling list to JSON using Jackson
                .setHeader("Content-Type", constant("application/json"))
                .routeId("GetOrderHistoryRoute")
                .description("Get order history by customer ID");
    }
}
