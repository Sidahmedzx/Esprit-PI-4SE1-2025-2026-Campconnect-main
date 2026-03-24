package tn.esprit.exam.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import tn.esprit.exam.entity.CampingSite; // ← Import de l'entité CORRECTE

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampingSiteDTO {
    private Long id;

    @NotBlank(message = "Site name is required")
    private String name;

    private String description;
    private String location;
    private String address;

    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal pricePerNight;

    @NotNull(message = "Capacity is required")
    @Min(value = 1)
    private Integer capacity;

    @NotBlank(message = "Category is required")
    private String category;

    // ✅ Trim pour éviter les espaces dans les URLs (problème d'image Firefox)
    private String imageUrl;

    private Boolean hasWifi;
    private Boolean hasParking;
    private Boolean hasRestrooms;
    private Boolean hasShowers;
    private Boolean hasElectricity;
    private Boolean hasPetFriendly;

    private Boolean isActive;
    private Boolean isVerified;

    @DecimalMin(value = "0.0")
    private BigDecimal rating;

    @Min(value = 0)
    private Integer reviewCount;

    // ✅ CHAMPS DU PROPRIÉTAIRE (sans charger l'objet User complet)
    private Long ownerId;
    private String ownerName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ✅ MÉTHODE DE CONVERSION : CampingSite Entity → DTO
    public static CampingSiteDTO fromEntity(CampingSite campingSite) {
        if (campingSite == null) return null;

        return CampingSiteDTO.builder()
                .id(campingSite.getId())
                .name(campingSite.getName())
                .description(campingSite.getDescription())
                .location(campingSite.getLocation())
                .address(campingSite.getAddress())
                .pricePerNight(campingSite.getPricePerNight())
                .capacity(campingSite.getCapacity())
                .category(campingSite.getCategory())
                // ✅ Trim de l'URL pour éviter les erreurs de chargement d'image
                .imageUrl(campingSite.getImageUrl() != null ? campingSite.getImageUrl().trim() : null)
                .hasWifi(campingSite.getHasWifi())
                .hasParking(campingSite.getHasParking())
                .hasRestrooms(campingSite.getHasRestrooms())
                .hasShowers(campingSite.getHasShowers())
                .hasElectricity(campingSite.getHasElectricity())
                .hasPetFriendly(campingSite.getHasPetFriendly())
                .isActive(campingSite.getIsActive())
                .isVerified(campingSite.getIsVerified())
                .rating(campingSite.getRating())
                .reviewCount(campingSite.getReviewCount())
                // ✅ Accès SÉCURISÉ à owner (évite LazyInitializationException)
                .ownerId(campingSite.getOwner() != null ? campingSite.getOwner().getId() : null)
                // ⚠️ Remplacez getFullName() par la méthode réelle de votre entité User
                .ownerName(campingSite.getOwner() != null ? campingSite.getOwner().getFullName() : null)
                .createdAt(campingSite.getCreatedAt())
                .updatedAt(campingSite.getUpdatedAt())
                .build();
    }
}