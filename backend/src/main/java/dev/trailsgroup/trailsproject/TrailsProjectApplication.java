package dev.trailsgroup.trailsproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TrailsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrailsProjectApplication.class, args);
    }

}
