package com.myfood.services;

import com.myfood.domain.user.AdminRegisterDTO;
import com.myfood.domain.user.UserDTO;
import com.myfood.domain.user.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    public Optional<Boolean> activateCompany(UUID id) {
        return companyService.activateCompany(id);
    }

    public UserDTO createAdminUser(AdminRegisterDTO data) {
        var user = new UserRegisterDTO(data.name(), data.email(), data.password(), data.role());
        return userService.registerAdmmin(user, data.companyId());
    }
}
