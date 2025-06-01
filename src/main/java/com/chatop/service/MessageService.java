package com.chatop.service;

import com.chatop.dto.MessageDTO;
import com.chatop.model.Messages;
import com.chatop.model.Rentals;
import com.chatop.model.Users;
import com.chatop.repository.MessagesRepository;
import com.chatop.repository.RentalsRepository;
import com.chatop.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {

    private final MessagesRepository messagesRepository;
    private final UsersRepository usersRepository;
    private final RentalsRepository rentalsRepository;

    public MessageService(
            MessagesRepository messagesRepository,
            UsersRepository usersRepository,
            RentalsRepository rentalsRepository
    ) {
        this.messagesRepository = messagesRepository;
        this.usersRepository = usersRepository;
        this.rentalsRepository = rentalsRepository;
    }


    public void createMessage(MessageDTO dto) {

        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("L’ID utilisateur ne peut pas être null");
        }
        if (dto.getRentalId() == null) {
            throw new IllegalArgumentException("L’ID location ne peut pas être null");
        }

        Users user = usersRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Utilisateur non trouvé (id=" + dto.getUserId() + ")"
                ));

        Rentals rental = rentalsRepository.findById(dto.getRentalId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Location non trouvée (id=" + dto.getRentalId() + ")"
                ));

        Messages msg = new Messages();
        msg.setMessage(dto.getMessage());
        msg.setUser(user);
        msg.setRental(rental);
        msg.setCreated_at(LocalDateTime.now());
        msg.setUpdated_at(LocalDateTime.now());

        messagesRepository.save(msg);
    }
}
