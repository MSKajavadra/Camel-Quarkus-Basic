package org.acme.route;

import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Delivery;
import org.acme.service.DeliveryService;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class DeliveryRoute extends RouteBuilder {

    @Inject
    DeliveryService deliveryService;

    @Override
    public void configure() throws Exception {
        restConfiguration().contextPath("/api").port(8080);

        rest("/delivery")
                .get("/{orderId}").to("direct:getDeliveryDetails")
                .post().type(Delivery.class).to("direct:createDelivery");

        from("direct:getDeliveryDetails")
                .bean(deliveryService, "getDeliveryDetails(${header.orderId})")
                .choice()
                .when(body().isNull())
                .setHeader("Content-Type", constant("application/json"))
                .setBody(simple("{ \"message\": \"Delivery details not found for order ID: ${header.orderId}\" }"))
                .setHeader("CamelHttpResponseCode", constant(404))
                .otherwise()
                .setHeader("Content-Type", constant("application/json"));

        from("direct:createDelivery")
                .bean(deliveryService, "createDelivery")
                .setBody(simple("Delivery created successfully"))
                .setHeader("Content-Type", constant("application/json"));
    }
}
