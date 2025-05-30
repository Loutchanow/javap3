package com.chatop.controllers;

import com.chatop.dto.RentalDTO;
import com.chatop.model.Rentals;
import com.chatop.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public List<Rentals> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rentals> getRental(@PathVariable Long id) {
        return rentalService.getRentalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Rentals> createRental(
            @PathVariable Long userId,
            @RequestParam("name") String name,
            @RequestParam("surface") BigDecimal surface,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @RequestParam(value = "picture", required = false) MultipartFile picture
    ) {
        RentalDTO dto = new RentalDTO();
        dto.setName(name);
        dto.setSurface(surface);
        dto.setPrice(price);
        dto.setDescription(description);
        String pictureUrl = picture != null ? picture.getOriginalFilename() : ""; 
        Rentals created = rentalService.createRental(userId, dto, pictureUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Rentals> updateRental(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") BigDecimal surface,
            @RequestParam("price") BigDecimal price,
            @RequestParam("description") String description,
            @RequestParam(value = "picture", required = false) MultipartFile picture
    ) {
        RentalDTO dto = new RentalDTO();
        dto.setName(name);
        dto.setSurface(surface);
        dto.setPrice(price);
        dto.setDescription(description);

        Rentals updatedRental = rentalService.updateRental(id, dto);

        return ResponseEntity.ok(updatedRental);
    }
}


//package com.chatop.controllers;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//
//@RestController
//@RequestMapping("/api/rentals")
//@SecurityRequirement(name = "bearerAuth")
//public class RentalController {
//
//	
//    @GetMapping
//    public String getAll() {
//        return "Get all rentals endpoint";
//    }
//
//    @GetMapping("/{id}")
//    public String getOne(@PathVariable Long id) {
//        return "Get rental with ID: " + id;
//    }
//
//    @PostMapping
//    public String create() {
//        return "Create rental endpoint";
//    }
//
//    @PutMapping("/{id}")
//    public String update(@PathVariable Long id) {
//        return "Update rental with ID: " + id;
//    }
//}
