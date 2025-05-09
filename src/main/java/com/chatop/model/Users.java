package com.chatop.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "USERS")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;

	private String name;

	private String password;

	private LocalDateTime created_at;

	private LocalDateTime updated_at;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private List<Rentals> rentals;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Messages> messages;
}
