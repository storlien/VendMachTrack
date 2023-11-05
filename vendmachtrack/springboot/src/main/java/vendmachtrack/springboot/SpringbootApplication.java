package vendmachtrack.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootApplication {

    @Bean
    public String fileName() {
        return "tracker.json";
    }

    public static void main(final String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}