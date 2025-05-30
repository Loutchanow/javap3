package com.chatop.repository;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.chatop.model.Rentals;

@Repository
public interface RentalsRepository extends JpaRepository<Rentals, Long> {
    List<Rentals> findAll();
}