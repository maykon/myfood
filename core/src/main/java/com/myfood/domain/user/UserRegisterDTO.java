package com.myfood.domain.user;

public record UserRegisterDTO(String name, String email, String password, UserRole role) {
}
