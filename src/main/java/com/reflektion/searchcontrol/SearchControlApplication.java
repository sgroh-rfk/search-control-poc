package com.reflektion.searchcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SearchControlApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SearchControlApplication.class, args);
        
        /*
        Debug wich beans are being provided
        System.out.println("Let's inspect the beans provided by Spring Boot:");
        
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }*/
    }

}

