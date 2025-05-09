package com.chatop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Userscontroller {

    @GetMapping("/test")
    public String testRoute() {
        System.out.println(" La route test");
        return "La route fonctionne ";
    }
}