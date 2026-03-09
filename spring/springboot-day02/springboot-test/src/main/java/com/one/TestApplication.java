package com.one;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestApplication {

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                System.out.println("applicationArguments:" + args);
                System.out.println(args.getNonOptionArgs());
                System.out.println(args.getOptionNames());
            }
        };
    }


    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                for (String arg : args) {
                    System.out.println("commandLineRunner:" + arg);
                }
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
