package com.myfood.domain.company;

import java.time.Instant;
import java.util.UUID;

public record CompanyDTO(UUID id, String name, String address, String phone, String cnpj, FoodCategory category,
                         String description, Integer distance, Integer rating, Boolean opened, Instant createdAt) {
}
