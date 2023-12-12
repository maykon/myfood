package com.myfood.services;

import com.myfood.domain.company.Company;
import com.myfood.domain.company.CompanyDTO;
import com.myfood.domain.company.CompanyListDTO;
import com.myfood.domain.company.CompanyRegisterDTO;
import com.myfood.domain.user.UserRegisterDTO;
import com.myfood.domain.user.UserRole;
import com.myfood.repositories.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyService {
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyRepository repository;

    @Transactional
    public CompanyDTO register(CompanyRegisterDTO data) {
        var newCompany = new Company(data.name(), data.address(), data.phone(), data.cnpj(), data.category(), data.description());
        var createdCompany = repository.save(newCompany);
        repository.flush();
        userService.register(new UserRegisterDTO(data.name(), data.email(), data.password(), UserRole.MERCHANT), createdCompany.getId());
        return createdCompany.toDTO();
    }

    public List<CompanyListDTO> findAllCompanies() {
        return repository.findAllCompanies();
    }

    public Optional<CompanyDTO> findCompanyById(UUID id) {
        return repository.findCompanyById(id);
    }

    public Optional<CompanyDTO> updateCompany(UUID id, CompanyDTO data) {
        var company = repository.findByIdAndActiveTrue(id);
        if (company.isEmpty()) {
            return Optional.empty();
        }
        var updateCompany = company.get().update(data);
        var updatedCompany = repository.save(updateCompany);
        return Optional.of(updatedCompany.toDTO());
    }

    @Transactional
    public Optional<CompanyDTO> openCompany(UUID id, Boolean opened) {
        var company = repository.findByIdAndActiveTrue(id);
        if (company.isEmpty()) {
            return Optional.empty();
        }
        repository.openCompany(id, opened);
        var companyOpened = company.get();
        companyOpened.setOpened(opened);
        return Optional.of(companyOpened.toDTO());
    }

    private Optional<Boolean> activateDeactivateCompany(UUID id, Boolean shouldActivate) {
        var company = repository.findById(id);
        if (company.isEmpty()) {
            return Optional.empty();
        }
        repository.activeCompany(id, shouldActivate);
        return Optional.of(true);
    }

    @Transactional
    public Optional<Boolean> deleteCompany(UUID id) {
        return activateDeactivateCompany(id, false);
    }

    @Transactional
    public Optional<Boolean> activateCompany(UUID id) {
        return activateDeactivateCompany(id, true);
    }
}
