package com.bhumi.Agriculture.repository;

import com.bhumi.Agriculture.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserId(Long userId);
}
