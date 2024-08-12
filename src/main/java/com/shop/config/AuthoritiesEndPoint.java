package com.shop.config;

import lombok.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;

@Endpoint(id = "authorities")
@Component
@Value
public class AuthoritiesEndPoint {
    /*todo: get authenticated count from somewhere*/
    @ReadOperation
    Map<String, Long> count() {
        return Map.of("count", 2L);
    }
}
