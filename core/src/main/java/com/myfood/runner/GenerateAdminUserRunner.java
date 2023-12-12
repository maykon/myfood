package com.myfood.runner;

import com.myfood.domain.user.AdminRegisterDTO;
import com.myfood.domain.user.UserRole;
import com.myfood.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class GenerateAdminUserRunner implements CommandLineRunner {
    @Value("${spring.profiles.active:dev}")
    private String profile;

    @Autowired
    private AdminService adminService;

    private String getAdminPassword() {
        if (profile.equals("dev")) {
            return "admin";
        }
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[10];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("GenerateAdminUserRunner --> Profile: ".concat(profile));
        var password = getAdminPassword();
        var userDto = new AdminRegisterDTO("Super Admin", "admin@myfood.com", password, UserRole.ADMIN, null);
        try {
            var createdUser = adminService.createAdminUser(userDto);
            System.out.println("GenerateAdminUserRunner --> Admin created...");
            System.out.println("GenerateAdminUserRunner --> Admin password generated: ".concat(password));
            System.out.println(createdUser);
        } catch (Exception ignored) {

        }
    }
}
