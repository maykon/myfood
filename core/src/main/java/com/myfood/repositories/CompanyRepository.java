package com.myfood.repositories;

import com.myfood.domain.company.Company;
import com.myfood.domain.company.CompanyDTO;
import com.myfood.domain.company.CompanyListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    @Query("""
            SELECT new com.myfood.domain.company.CompanyListDTO(c.id, c.name, c.address, c.phone, c.category,
            c.description, c.opened) FROM Company c WHERE c.active = true""")
    List<CompanyListDTO> findAllCompanies();

    @Query("""
            SELECT new com.myfood.domain.company.CompanyDTO(c.id, c.name, c.address, c.phone, c.cnpj, c.category,
            c.description, c.distance, c.rating, c.opened, c.createdAt) FROM Company c WHERE c.active = true and id =:id""")
    Optional<CompanyDTO> findCompanyById(@Param("id") UUID id);

    Optional<Company> findByIdAndActiveTrue(UUID id);

    @Modifying
    @Query("UPDATE Company c SET c.opened = ?2 WHERE c.id IN ?1")
    void openCompany(UUID id, Boolean opened);

    @Modifying
    @Query("UPDATE Company c SET c.active = ?2 WHERE c.id IN ?1")
    void activeCompany(UUID id, Boolean active);
}
