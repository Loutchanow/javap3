package com.chatop.dto;

import lombok.Data;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

@Data
public class RentalDTO {
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String description;
    private MultipartFile picture;
}