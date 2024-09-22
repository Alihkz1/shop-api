package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ShopApplication {
    /* http://localhost:8081/swagger-ui/index.html#/ */
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

}
