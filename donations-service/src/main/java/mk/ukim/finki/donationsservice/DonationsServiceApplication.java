package mk.ukim.finki.donationsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DonationsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonationsServiceApplication.class, args);
    }

}
