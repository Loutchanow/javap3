package com.chatop.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RentalResponseDTO {
	   private Long id;
	    private String name;
	    private BigDecimal surface;
	    private BigDecimal price;
	    private String picture;
	    private String description;
	    private Long owner_id;
	    private String created_at;
	    private String updated_at;
}
