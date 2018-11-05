package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@SpringBootApplication
@RestController
public class TransitApplication {

    private final Log LOG = LogFactory.getLog(getClass());

    @Autowired
    County county;

    @GetMapping("/connected")
    public boolean isConnected(@RequestParam String origin, @RequestParam String destination) {

        City originCity = county.getCity(origin.toUpperCase());
        City destCity = county.getCity(destination.toUpperCase());

        Objects.requireNonNull(originCity);
        Objects.requireNonNull(destCity);

        return Commuter.commute(originCity, destCity);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String cityError() {
        return "Either ORIGIN or DESTINATION is invalid";
    }

    public static void main(String[] args) {
        SpringApplication.run(TransitApplication.class, args);
    }

}
