package com.bhumi.Agriculture.controller;

import com.bhumi.Agriculture.model.Rental;
import com.bhumi.Agriculture.service.BookingService;
import com.bhumi.Agriculture.service.EquipmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class EquipmentController {

    @Autowired
    private EquipmentService service;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("equipments", service.searchEquipment(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("equipments", service.getAllEquipment());
        }
        return "index";
    }

    @PostMapping("/rent")
    public String rent(@RequestParam Long id, 
                       @RequestParam LocalDate fromDate, 
                       @RequestParam LocalDate toDate, 
                       HttpSession session, 
                       Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            Rental rental = bookingService.createBooking(userId, id, fromDate, toDate);
            return "redirect:/receipt?bookingId=" + rental.getId();
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("equipments", service.getAllEquipment());
            return "index";
        }
    }

    @GetMapping("/bookings")
    public String myBookings(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("bookings", bookingService.getUserBookings(userId));
        return "bookings";
    }

    @PostMapping("/cancel-booking")
    public String cancelBooking(@RequestParam Long bookingId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        bookingService.cancelBooking(bookingId);
        return "redirect:/bookings";
    }

    @GetMapping("/receipt")
    public String showReceipt(@RequestParam Long bookingId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        Rental rental = bookingService.getBooking(bookingId);
        if (rental == null || !rental.getUser().getId().equals(userId)) {
            return "redirect:/";
        }
        
        model.addAttribute("booking", rental);
        long days = java.time.temporal.ChronoUnit.DAYS.between(rental.getFromDate(), rental.getToDate());
        model.addAttribute("days", days);
        return "receipt";
    }
}
