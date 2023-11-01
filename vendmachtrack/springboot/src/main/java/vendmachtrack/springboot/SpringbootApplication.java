package vendmachtrack.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The {@code SpringbootApplication} class serves as the main entry point for the Spring Boot application.
 */
@SpringBootApplication
public class SpringbootApplication {

    @Bean
    public String fileName() {
        return "tracker.json";
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}