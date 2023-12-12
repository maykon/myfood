package com.myfood.controllers;

import com.myfood.domain.company.CompanyDTO;
import com.myfood.domain.company.CompanyListDTO;
import com.myfood.domain.company.CompanyRegisterDTO;
import com.myfood.services.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<CompanyDTO> register(@RequestBody @Valid CompanyRegisterDTO data) {
        try {
            var newUser = companyService.register(data);
            return ResponseEntity.ok(newUser);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<CompanyListDTO>> getAllCompanies() {
        return ResponseEntity.ok(companyService.findAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CompanyDTO>> getCompanyById(@PathVariable @NotNull UUID id) {
        var company = companyService.findCompanyById(id);
        if (company.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<CompanyDTO>> updateCompany(@PathVariable @NotNull UUID id, @RequestBody CompanyDTO data) {
        var company = companyService.updateCompany(id, data);
        if (company.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company);
    }

    @PutMapping("/{id}/open/{opened}")
    public ResponseEntity<Optional<CompanyDTO>> openCompany(@PathVariable @NotNull UUID id, @PathVariable @NotNull Boolean opened) {
        var company = companyService.openCompany(id, opened);
        if (company.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable @NotNull UUID id) {
        var company = companyService.deleteCompany(id);
        if (company.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
