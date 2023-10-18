package com.tripgago.tripgagoback.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
    @RestController
    public class Testcontroller {
        @GetMapping("/api/users")
        public String getUsers() {
            return "User data from Spring Boot";
        }
    }

