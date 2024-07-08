package com.mobile.place;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.mobile.place.entity"})
@EnableJpaRepositories(basePackages = {"com.mobile.place.repository"})
public class MobilePlaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobilePlaceApplication.class, args);
    }

}
