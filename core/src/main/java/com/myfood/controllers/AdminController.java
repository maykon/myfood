package com.myfood.controllers;

import com.myfood.domain.user.AdminRegisterDTO;
import com.myfood.domain.user.UserDTO;
import com.myfood.services.AdminService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/companies/{id}/activate")
    public ResponseEntity<Void> activateCompany(@PathVariable("id") @NotNull UUID id) {
        var company = adminService.activateCompany(id);
        if (company.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> registerAdminUser(@RequestBody AdminRegisterDTO data) {
        try {
            var user = adminService.createAdminUser(data);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
