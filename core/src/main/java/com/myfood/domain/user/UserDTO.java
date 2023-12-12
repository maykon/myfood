package com.myfood.domain.user;

import java.time.Instant;
import java.util.UUID;

public record UserDTO(String name, String email, UserRole role, UUID companyId, Instant createdAt) {
}
