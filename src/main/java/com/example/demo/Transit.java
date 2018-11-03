package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Transit {


    @GetMapping("/connected")
    public String isConnected(String A, String B) {


        return "No";
    }

    public static void main(String[] args) {
        SpringApplication.run(Transit.class, args);
    }

}
