package com.myfood.domain.company;

public record CompanyRegisterDTO(String name, String address, String phone, String cnpj, FoodCategory category,
                                 String description, String email, String password) {
}
