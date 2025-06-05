package com.chatop.service;

import com.chatop.dto.RentalDTO;
import com.chatop.dto.RentalResponseDTO;
import com.chatop.dto.UsersDTO;
import com.chatop.model.Rentals;
import com.chatop.model.Users;
import com.chatop.repository.RentalsRepository;
import com.chatop.repository.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.net.ftp.FTP;

@Service
public class RentalService {

    private final RentalsRepository rentalsRepository;
    private final UsersRepository usersRepository;

    public RentalService(RentalsRepository rentalsRepository, UsersRepository usersRepository) {
        this.rentalsRepository = rentalsRepository;
        this.usersRepository = usersRepository;
    }

    private String computeFinalUrl(String path)
    {
    	return "/api/images/file/" + path;
    }
    

    public List<RentalResponseDTO> getAllRentals() {
        return rentalsRepository.findAll().stream().map(rental -> {
            RentalResponseDTO dto = new RentalResponseDTO();
            dto.setId(rental.getId());
            dto.setName(rental.getName());
            dto.setSurface(rental.getSurface());
            dto.setPrice(rental.getPrice());
            dto.setPicture(computeFinalUrl(rental.getPicture()));
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
                dto.setPicture(computeFinalUrl(rental.getPicture()));
                dto.setDescription(rental.getDescription());
                dto.setOwner_id(rental.getOwner().getId());
                dto.setCreated_at(rental.getCreated_at().toString());
                dto.setUpdated_at(rental.getUpdated_at().toString());
                return dto;
            });
    }

    
    public Map<String, String> createRental(Principal principal, RentalDTO dto, MultipartFile file){

        Users owner = usersRepository
        	.findByEmail(principal.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        Rentals rental = new Rentals();
        rental.setName(dto.getName());
        rental.setSurface(dto.getSurface());
        rental.setPrice(dto.getPrice());
        rental.setDescription(dto.getDescription());
        rental.setOwner(owner);
        if (file != null && !file.isEmpty()) {
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect("localhost");
                ftpClient.login("ftpuser", "1234"); 
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

                String filename = file.getOriginalFilename();
                String remotePath = "/ftp/uploads/" + filename;

                try (InputStream inputStream = file.getInputStream()) {
                    boolean uploaded = ftpClient.storeFile(remotePath, inputStream);
                    ftpClient.logout();
                    ftpClient.disconnect();

                    if (uploaded) {
                        rental.setPicture(filename);
                    } else {
                        throw new RuntimeException("Échec de l'upload FTP");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("Erreur FTP: " + e.getMessage(), e);
            }
        } else {
            rental.setPicture(null);
        }

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

