package org.acme.route;

import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Order;
import org.acme.service.OrderService;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class OrderRoute extends RouteBuilder {

    @Inject
    OrderService orderService;

    @Override
    public void configure() throws Exception {
        restConfiguration().contextPath("/api").port(8080);

        rest("/orders")
                .post().type(Order.class).to("direct:placeOrder")
                .get("/{customerId}").to("direct:getOrderHistory");

        from("direct:placeOrder")
                .bean(orderService, "placeOrder")
                .setHeader("Content-Type", constant("application/json"));

        from("direct:getOrderHistory")
                .bean(orderService, "getOrderHistory(${header.customerId})")
                .setHeader("Content-Type", constant("application/json"));
    }
}
