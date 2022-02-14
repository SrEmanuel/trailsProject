package dev.trailsgroup.trailsproject.config;

import dev.trailsgroup.trailsproject.entities.*;
import dev.trailsgroup.trailsproject.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;



    @Override
    public void run(String... args) throws Exception {

        User u1 = new User(null, "Emanuel", "123", "emanuelmartins@gmail.com", 1, true);
        User u2 = new User(null, "João", "123", "joao@gmail.com", 1, true);
        User u3 = new User(null, "Edmarcos", "123", "edmarcos@gmail.com", 1, true);
        User u4 = new User(null, "Giulian", "123", "Giulian@gmail.com", 1, true);
        User u5 = new User(null, "Tatiane", "123", "Tatiane@gmail.com", 1, true);

        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5));

        Course c1 = new Course(null, "Matemática", "www.google.com");
        Course c2 = new Course(null, "Portugues", "www.google.com");
        Course c3 = new Course(null, "História", "www.google.com");
        Course c4 = new Course(null, "Geografia", "www.google.com");
        Course c5 = new Course(null, "Química", "www.google.com");
        Course c6 = new Course(null, "Física", "www.google.com");
        Course c7 = new Course(null, "Banco de Dados", "www.google.com");

        courseRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6));

        Topic t1 = new Topic(null, "Matemática Aplicada", 1, c1);
        Topic t2 = new Topic(null, "Matemática para o Ensino Médio", 2, c1);
        Topic t3 = new Topic(null, "Literatura", 1, c2);
        Topic t4 = new Topic(null, "Arte Moderna", 2, c2);
        Topic t5 = new Topic(null, "História Colonial do Brasil", 1, c3);
        Topic t6 = new Topic(null, "Ditadura Militar", 1, c3);

        topicRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5, t6));

        Subject s1 = new Subject(null, "Vamos aprender Matemática?", "https://google.com",
                "1º Ano", "<h1> Hello World!</h1>", t2);
        Subject s2 = new Subject(null, "O que é Literatura?", "https://google.com",
                "1º Ano", "<h1> Hello World!</h1>", t3);
        Subject s3 = new Subject(null, "Matemática Financeira", "https://google.com",
                "1º Ano", "<h1> Hello World!</h1>", t2);
        Subject s4 = new Subject(null, "Vamos aprender Matemática?", "https://google.com",
                "1º Ano", "<h1> Hello World!</h1>", t1);
        Subject s5 = new Subject(null, "Como aplicar a literatura nas nossas vidas?", "https://google.com",
                "1º Ano", "<h1> Hello World!</h1>", t3);
        Subject s6 = new Subject(null, "Introdução à Arte Moderna", "https://google.com",
                "1º Ano", "<h1> Hello World!</h1>", t4);
        Subject s7 = new Subject(null, "O Descobrimento do Brasil", "https://google.com",
                "1º Ano", "<h1> Hello World!</h1>", t5);

        subjectRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6, s7));


        UserCourse us1 = new UserCourse(c1, u1);
        UserCourse us2 = new UserCourse(c2, u1);
        UserCourse us3 = new UserCourse(c3, u1);
        UserCourse us4 = new UserCourse(c2, u2);
        UserCourse us5 = new UserCourse(c4, u2);
        UserCourse us6 = new UserCourse(c1, u3);
        UserCourse us7 = new UserCourse(c2, u1);
        UserCourse us8 = new UserCourse(c5, u4);
        UserCourse us9 = new UserCourse(c6, u4);
        UserCourse us10 = new UserCourse(c6, u5);

        userCourseRepository.saveAll(Arrays.asList(us1, us2, us3, us4, us5, us6, us7, us8, us9, us10));
    }

}
