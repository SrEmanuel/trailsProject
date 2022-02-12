package dev.trailsgroup.trailsproject.config;

import dev.trailsgroup.trailsproject.entities.User;
import dev.trailsgroup.trailsproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User(null, "Emanuel", "123", "emanuelmartins@gmail.com", 1, true);
        userRepository.save(u1);


    }

}
