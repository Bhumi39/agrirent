package com.bhumi.Agriculture.service;

import com.bhumi.Agriculture.model.Equipment;
import com.bhumi.Agriculture.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository repository;

    public List<Equipment> getAllEquipment() {
        return repository.findAll();
    }

    public List<Equipment> searchEquipment(String name) {
        if (name == null || name.isEmpty()) {
            return getAllEquipment();
        }
        return repository.findByNameContainingIgnoreCase(name);
    }
}
