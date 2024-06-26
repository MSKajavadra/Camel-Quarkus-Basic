package org.acme.route;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.service.InventoryService;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
@Api(value = "Inventory API", produces = "application/json")
public class InventoryRoute extends RouteBuilder {

    @Inject
    InventoryService inventoryService;

    @Override
    public void configure() throws Exception {
        restConfiguration().contextPath("/api").port(8080);

        rest("/inventory")
                .put("/{storeId}/{productId}")
                .to("direct:updateInventory");

        from("direct:updateInventory")
                .process(exchange -> {
                    Long storeId = Long.parseLong(exchange.getIn().getHeader("storeId", String.class));
                    Long productId = Long.parseLong(exchange.getIn().getHeader("productId", String.class));
                    int quantity = exchange.getIn().getBody(Integer.class);
                    inventoryService.updateInventory(storeId, productId, quantity);
                })
                .setBody(constant(""))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("CamelHttpResponseCode", constant(200))
                .routeId("UpdateInventoryRoute")
                .description("Update inventory for a given store and product");
    }
}
