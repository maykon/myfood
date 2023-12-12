package com.myfood.domain.user;

public record UpdatePasswordDTO(String email, String oldPassword, String newPassword, String confirmPassword) {
}
