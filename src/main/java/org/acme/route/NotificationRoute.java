package org.acme.route;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Notification;
import org.acme.service.NotificationService;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
@Api(value = "Notification API", produces = "application/json")
public class NotificationRoute extends RouteBuilder {

    @Inject
    NotificationService notificationService;

    @Override
    public void configure() throws Exception {
        restConfiguration().contextPath("/api").port(8080);

        rest("/notifications")
                .post().type(Notification.class)
                .to("direct:sendNotification");

        from("direct:sendNotification")
                .bean(notificationService, "sendNotification")
                .setBody(simple("Notification sent successfully"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("CamelHttpResponseCode", constant(200))
                .routeId("SendNotificationRoute")
                .description("Send notification");
    }
}
