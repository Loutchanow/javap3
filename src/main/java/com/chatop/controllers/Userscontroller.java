package com.chatop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class Userscontroller {

    @GetMapping("/test")
    public String testRoute() {
        System.out.println(" La route test");
        return "La route fonctionne ";
    }
}