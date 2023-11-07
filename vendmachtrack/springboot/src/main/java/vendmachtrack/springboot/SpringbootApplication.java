package vendmachtrack.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The {@code SpringbootApplication} class serves as the main entry point for the Spring Boot application.
 */
@SpringBootApplication
public class SpringbootApplication {

    /**
     * The file name of the .json file storing the {@code MachineTracker} object
     */
    private static final String FILE_NAME = "tracker.json";

    /**
     * Method for returning the file name of the .json file storing the {@code MachineTracker} object
     *
     * @return File name of the .json file
     */
    @Bean
    public String fileName() {
        return FILE_NAME;
    }

    public static void main(final String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}