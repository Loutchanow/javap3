package com.chatop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

    @GetMapping
    public String getAll() {
        return "Get all rentals endpoint";
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id) {
        return "Get rental with ID: " + id;
    }

    @PostMapping
    public String create() {
        return "Create rental endpoint";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id) {
        return "Update rental with ID: " + id;
    }
}
