package com.myfood.domain.company;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "companies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String address;
    private String phone;
    private String cnpj;
    @Enumerated(EnumType.STRING)
    private FoodCategory category;
    private Integer distance;
    private Integer rating;
    private String description;
    private Boolean opened;
    private Boolean active;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    public Company(String name, String address, String phone, String cnpj, FoodCategory category, String description) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.cnpj = cnpj;
        this.category = category;
        this.description = description;
        this.active = true;
        this.opened = false;
        this.rating = 5;
        this.distance = (int) (Math.random() * 10_000) + 1_000;
    }

    public Company update(CompanyDTO dto) {
        this.name = dto.name();
        this.address = dto.address();
        this.phone = dto.phone();
        this.cnpj = dto.cnpj();
        this.category = dto.category();
        this.description = dto.description();
        return this;
    }

    public CompanyDTO toDTO() {
        return new CompanyDTO(getId(), getName(), getAddress(), getPhone(), getCnpj(), getCategory(), getDescription(),
                getDistance(), getRating(), getOpened(), getCreatedAt()
        );
    }
}
