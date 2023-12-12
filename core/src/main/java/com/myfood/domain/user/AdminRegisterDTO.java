package com.myfood.domain.user;

import java.util.UUID;

public record AdminRegisterDTO(String name, String email, String password, UserRole role, UUID companyId) {
}
