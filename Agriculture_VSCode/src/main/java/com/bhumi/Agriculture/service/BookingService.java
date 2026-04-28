package com.bhumi.Agriculture.service;

import com.bhumi.Agriculture.model.Rental;
import com.bhumi.Agriculture.model.Equipment;
import com.bhumi.Agriculture.model.User;
import com.bhumi.Agriculture.repository.BookingRepository;
import com.bhumi.Agriculture.repository.EquipmentRepository;
import com.bhumi.Agriculture.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private UserRepository userRepository;

    public Rental createBooking(Long userId, Long equipmentId, LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isAfter(toDate) || fromDate.isEqual(toDate)) {
            throw new IllegalArgumentException("From date must be before To date");
        }

        Optional<Equipment> equipmentOpt = equipmentRepository.findById(equipmentId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (equipmentOpt.isPresent() && userOpt.isPresent()) {
            Equipment equipment = equipmentOpt.get();
            if (!equipment.isAvailability()) {
                throw new IllegalStateException("Equipment is not available");
            }

            User user = userOpt.get();
            
            long days = ChronoUnit.DAYS.between(fromDate, toDate);
            double totalCost = days * equipment.getPricePerDay();

            Rental rental = new Rental();
            rental.setUser(user);
            rental.setEquipment(equipment);
            rental.setFromDate(fromDate);
            rental.setToDate(toDate);
            rental.setTotalCost(totalCost);

            equipment.setAvailability(false);
            equipmentRepository.save(equipment);

            return bookingRepository.save(rental);
        }
        throw new IllegalArgumentException("Invalid user or equipment");
    }

    public List<Rental> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
    
    public void cancelBooking(Long bookingId) {
        Optional<Rental> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isPresent()) {
            Rental rental = bookingOpt.get();
            Equipment equipment = rental.getEquipment();
            equipment.setAvailability(true);
            equipmentRepository.save(equipment);
            bookingRepository.delete(rental);
        }
    }
    
    public Rental getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }
}
