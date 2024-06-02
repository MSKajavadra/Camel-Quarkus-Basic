package org.acme.route;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.CustomerOrder;
import org.acme.service.OrderService;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
@Api(value = "Order API", produces = "application/json")
public class OrderRoute extends RouteBuilder {

    @Inject
    OrderService orderService;

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
                .setHeader("Content-Type", constant("application/json"))
                .routeId("PlaceOrderRoute")
                .description("Place a new order");

        from("direct:getOrderHistory")
                .bean(orderService, "getOrderHistory(${header.customerId})")
                .setHeader("Content-Type", constant("application/json"))
                .routeId("GetOrderHistoryRoute")
                .description("Get order history by customer ID");
    }
}
