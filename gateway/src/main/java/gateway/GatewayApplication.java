package gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

//@SpringCloudApplication
@SpringBootApplication
//@EnableDiscoveryClient
public class GatewayApplication {


/*    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("testGateWay", r -> r.path("/testGateWay")
                        .uri("http://localhost:8761/")
                )
*//*				.route(r -> r.path("/userapi3/**")
						.filters(f -> f.addResponseHeader("X-AnotherHeader", "testapi3"))
						.uri("lb://user-service/")
				)*//*
                .build();
    }*/

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
