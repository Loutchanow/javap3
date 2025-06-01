package com.chatop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {

    @NotNull(message = "Le texte du message ne peut pas être vide")
    private String message;

    @JsonProperty("user_id")
    @NotNull(message = "L’ID utilisateur est requis")
    private Long userId;

    @JsonProperty("rental_id")
    @NotNull(message = "L’ID de la location est requis")
    private Long rentalId;
}