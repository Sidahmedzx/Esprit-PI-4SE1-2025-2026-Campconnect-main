package tn.esprit.exam.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import tn.esprit.exam.entity.Equipment; // ← Import de l'entité

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentDTO {
    private Long id;

    @NotBlank(message = "Equipment name is required")
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Price per day is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal pricePerDay;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0)
    private Integer stockQuantity;

    private Integer availableQuantity;
    private String imageUrl;
    private String specifications;
    private Boolean isActive;
    private BigDecimal rating;
    private Integer reviewCount;

    // Champs du provider (sans charger l'objet User complet)
    private Long providerId;
    private String providerName;

    // ✅ MÉTHODE DE CONVERSION : Entity → DTO
    public static EquipmentDTO fromEntity(Equipment equipment) {
        if (equipment == null) return null;

        return EquipmentDTO.builder()
                .id(equipment.getId())
                .name(equipment.getName())
                .description(equipment.getDescription())
                .category(equipment.getCategory())
                .pricePerDay(equipment.getPricePerDay())
                .stockQuantity(equipment.getStockQuantity())
                .availableQuantity(equipment.getAvailableQuantity())
                .imageUrl(equipment.getImageUrl())
                .specifications(equipment.getSpecifications())
                .isActive(equipment.getIsActive())
                .rating(equipment.getRating())
                .reviewCount(equipment.getReviewCount())
                // ✅ Accès SÉCURISÉ au provider (vérification null + pas de proxy)
                .providerId(equipment.getProvider() != null ? equipment.getProvider().getId() : null)
                .providerName(equipment.getProvider() != null ? equipment.getProvider().getFullName() : null)
                .build();
    }
}