package com.chatop.controllers;

import com.chatop.dto.MessageDTO;
import com.chatop.service.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@SecurityRequirement(name = "bearerAuth")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody MessageDTO dto,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fe ->
                errors.put(fe.getField(), fe.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        messageService.createMessage(dto);

        Map<String, String> response = new HashMap<>();
        response.put("message", "sendd with succes");
        return ResponseEntity.ok(response);
    }
}
