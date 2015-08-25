package org.informatica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@EnableAutoConfiguration
@ComponentScan
public class EventApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(EventApp.class, args);
    }
   
}
