package com.myfood.domain.company;

import java.util.UUID;

public record CompanyListDTO(UUID id, String name, String address, String phone, FoodCategory category,
                             String description, Boolean opened) {
}
