package com.chatop.service;

import com.chatop.dto.RentalDTO;
import com.chatop.dto.RentalResponseDTO;
import com.chatop.dto.UsersDTO;
import com.chatop.model.Rentals;
import com.chatop.model.Users;
import com.chatop.repository.RentalsRepository;
import com.chatop.repository.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private final RentalsRepository rentalsRepository;
    private final UsersRepository usersRepository;

    public RentalService(RentalsRepository rentalsRepository, UsersRepository usersRepository) {
        this.rentalsRepository = rentalsRepository;
        this.usersRepository = usersRepository;
    }


    public List<RentalResponseDTO> getAllRentals() {
        return rentalsRepository.findAll().stream().map(rental -> {
            RentalResponseDTO dto = new RentalResponseDTO();
            dto.setId(rental.getId());
            dto.setName(rental.getName());
            dto.setSurface(rental.getSurface());
            dto.setPrice(rental.getPrice());
            dto.setPicture(rental.getPicture());
            dto.setDescription(rental.getDescription());
            dto.setOwner_id(rental.getOwner().getId());
            dto.setCreated_at(rental.getCreated_at().toString());
            dto.setUpdated_at(rental.getUpdated_at().toString());
            return dto;
        }).collect(Collectors.toList());
    }
    
    public Optional<RentalResponseDTO> getRentalByIdDTO(Long id) {
        return rentalsRepository.findById(id)
            .map(rental -> {
                RentalResponseDTO dto = new RentalResponseDTO();
                dto.setId(rental.getId());
                dto.setName(rental.getName());
                dto.setSurface(rental.getSurface());
                dto.setPrice(rental.getPrice());
                dto.setPicture(rental.getPicture());
                dto.setDescription(rental.getDescription());
                dto.setOwner_id(rental.getOwner().getId());
                dto.setCreated_at(rental.getCreated_at().toString());
                dto.setUpdated_at(rental.getUpdated_at().toString());
                return dto;
            });
    }

    
    public Map<String, String> createRental(Principal principal, RentalDTO dto) {
        Users owner = usersRepository
        	.findByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        Rentals rental = new Rentals();
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setDescription(dto.getDescription());
        rental.setOwner(owner);
        rental.setPicture("https://fastly.picsum.photos/id/237/200/300.jpg?hmac=TmmQSbShHz9CdQm0NkEjx1Dyh_Y984R9LpNrpvH2D_U");
        rental.setCreated_at(LocalDateTime.now());
        rental.setUpdated_at(LocalDateTime.now());

        rentalsRepository.save(rental);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental created !");
        return response;
    }
    
    public void updateRental(Long id, RentalDTO dto) {
        Rentals rental = rentalsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found"));

        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setDescription(dto.getDescription());
        rental.setUpdated_at(LocalDateTime.now());

        rentalsRepository.save(rental);
    }
}

