package org.acme.route;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.apache.camel.builder.RouteBuilder;

@Singleton
public class ComponentsExample extends RouteBuilder {
    @Override
    public void configure() throws Exception {
//        from("timer:myTimer?period=5000")
//                .setBody(simple("Good Morning!"))
//                .bean("beanExample", "method")
//                .to("log:Timer triggered. Fetching customer data...");
//
//
//        from("timer:myTimer?period=5000")
//                .setBody(simple("Good Morning!"))
//                .to("direct:processMessage");
//        from("direct:processMessage")
//                .bean("beanExample", "process")
//                .to("log:Processed Message");
//
//
//        from("file://F:\\Quarkus\\input")
//                .log("${body}")
//                .to("file:F:\\Quarkus\\output");
    }
}
@ApplicationScoped
@Named("beanExample")
class BeanExample{
    public String method(String body){
        return body + " - Processed by BeanExample.method";
    }
    public String process(String body){
        return body + " - Processed by BeanExample.process";
    }
}

