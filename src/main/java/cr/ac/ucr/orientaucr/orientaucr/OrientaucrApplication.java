package cr.ac.ucr.orientaucr.orientaucr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrientaucrApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrientaucrApplication.class, args);
    }

}
