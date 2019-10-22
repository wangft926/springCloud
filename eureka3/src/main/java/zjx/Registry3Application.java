package zjx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Registry3Application {

	public static void main(String[] args) {
		SpringApplication.run(Registry3Application.class, args);
	}
}
