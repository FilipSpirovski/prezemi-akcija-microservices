package mk.ukim.finki.initiativesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class InitiativesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InitiativesServiceApplication.class, args);
    }

}
