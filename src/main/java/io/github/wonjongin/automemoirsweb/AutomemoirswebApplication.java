package io.github.wonjongin.automemoirsweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutomemoirswebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomemoirswebApplication.class, args);
    }

}
