package br.com.project.sonora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class SonoraApplication {

    public static void main(String[] args) {
        SpringApplication.run(SonoraApplication.class, args);
    }

}
