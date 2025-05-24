package com.example.ms_commande;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.ms_commande.repository")
@EnableFeignClients(basePackages = "com.example.ms_commande.client")
public class MsCommandeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCommandeApplication.class, args);
    }

}
