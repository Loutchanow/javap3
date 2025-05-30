package com.chatop.controllers;

import com.chatop.dto.RentalDTO;
import com.chatop.dto.RentalResponseDTO;
import com.chatop.model.Rentals;
import com.chatop.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<RentalResponseDTO>>> getAllRentals() {
        List<RentalResponseDTO> rentals = rentalService.getAllRentals();
        Map<String, List<RentalResponseDTO>> response = new HashMap<>();
        response.put("rentals", rentals);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getRental(@PathVariable Long id) {
        return rentalService.getRentalByIdDTO(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/")
    public ResponseEntity<Map<String, String>> createRental(
            @ModelAttribute RentalDTO rentalDTO, Principal principal
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(rentalService.createRental(principal, rentalDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateRental(
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

        rentalService.updateRental(id, dto);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental updated !");
        return ResponseEntity.ok(response);
    }

}
    

