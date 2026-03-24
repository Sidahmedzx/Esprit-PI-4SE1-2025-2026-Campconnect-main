package tn.esprit.exam.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tn.esprit.exam.dto.EquipmentDTO; // ← Import du DTO
import tn.esprit.exam.entity.Equipment;
import tn.esprit.exam.repository.EquipmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentRepository equipmentRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public List<EquipmentDTO> getAll() {
        return equipmentRepository.findAll().stream()
                .map(EquipmentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/available")
    @Transactional(readOnly = true)
    public List<EquipmentDTO> getAvailable() {
        return equipmentRepository.findAll().stream()
                .filter(e -> Boolean.TRUE.equals(e.getIsActive())
                        && e.getAvailableQuantity() != null
                        && e.getAvailableQuantity() > 0)
                .map(EquipmentDTO::fromEntity)  // ← Conversion vers DTO
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    @Transactional(readOnly = true)
    public List<EquipmentDTO> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax) {
        return equipmentRepository.findAll().stream()
                .filter(e -> name == null || (e.getName() != null && e.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(e -> category == null || (e.getCategory() != null && e.getCategory().equalsIgnoreCase(category)))
                .filter(e -> priceMin == null || (e.getPricePerDay() != null && e.getPricePerDay().doubleValue() >= priceMin))
                .filter(e -> priceMax == null || (e.getPricePerDay() != null && e.getPricePerDay().doubleValue() <= priceMax))
                .map(EquipmentDTO::fromEntity)  // ← Conversion vers DTO
                .collect(Collectors.toList());
    }

    @GetMapping("/category/{category}")
    @Transactional(readOnly = true)
    public List<EquipmentDTO> getByCategory(@PathVariable String category) {
        return equipmentRepository.findAll().stream()
                .filter(e -> category.equalsIgnoreCase(e.getCategory()))
                .map(EquipmentDTO::fromEntity)  // ← Conversion vers DTO
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public EquipmentDTO getById(@PathVariable Long id) {
        return equipmentRepository.findById(id)
                .map(EquipmentDTO::fromEntity)  // ← Conversion vers DTO
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found: " + id));
    }

    // ⚠️ Pour les méthodes POST/PUT, vous pouvez garder Equipment en entrée
    // mais retourner DTO en sortie pour la cohérence

    @PostMapping
    @Transactional
    public EquipmentDTO create(@RequestBody Equipment equipment) {
        equipment.setId(null);
        equipment.setReservationEquipments(new java.util.HashSet<>());
        Equipment saved = equipmentRepository.save(equipment);
        return EquipmentDTO.fromEntity(saved);  // ← Retourne un DTO
    }

    @PutMapping("/{id}")
    @Transactional
    public EquipmentDTO update(@PathVariable Long id, @RequestBody Equipment equipment) {
        Equipment existing = equipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found: " + id));
        equipment.setId(id);
        equipment.setReservationEquipments(existing.getReservationEquipments());
        Equipment updated = equipmentRepository.save(equipment);
        return EquipmentDTO.fromEntity(updated);  // ← Retourne un DTO
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!equipmentRepository.existsById(id)) throw new IllegalArgumentException("Equipment not found: " + id);
        equipmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}