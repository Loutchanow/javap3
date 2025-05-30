package com.chatop.service;

import com.chatop.dto.RentalDTO;
import com.chatop.model.Rentals;
import com.chatop.model.Users;
import com.chatop.repository.RentalsRepository;
import com.chatop.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalsRepository rentalsRepository;
    private final UsersRepository usersRepository;

    public RentalService(RentalsRepository rentalsRepository, UsersRepository usersRepository) {
        this.rentalsRepository = rentalsRepository;
        this.usersRepository = usersRepository;
    }

    public List<Rentals> getAllRentals() {
        return rentalsRepository.findAll();
    }

    public Optional<Rentals> getRentalById(Long id) {
        return rentalsRepository.findById(id);
    }

    public Rentals createRental(Long userId, RentalDTO dto, String pictureUrl) {
        Users owner = usersRepository.findById(userId).orElseThrow();
        Rentals rental = new Rentals();
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setDescription(dto.getDescription());
        rental.setOwner(owner);
        rental.setPicture(pictureUrl);
        rental.setCreated_at(LocalDateTime.now());
        rental.setUpdated_at(LocalDateTime.now());
        return rentalsRepository.save(rental);
    }
    public Rentals updateRental(Long id, RentalDTO dto) {
        Rentals rental = rentalsRepository.findById(id).orElseThrow();
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setDescription(dto.getDescription());
        return rentalsRepository.save(rental);
    }
}

