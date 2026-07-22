package org.jobportal.portal.company.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobportal.portal.constants.ApplicationConstants;
import org.jobportal.portal.dto.CompanyDto;
import org.jobportal.portal.entity.Company;
import org.jobportal.portal.mapper.CompanyMapper;
import org.jobportal.portal.repository.CompanyRepository;
import org.jobportal.portal.company.service.ICompanyService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companyList = companyRepository.findAllWithJobsByStatus(ApplicationConstants.ACTIVE_STATUS);
        return companyMapper.toDtoList(companyList);
    }

    @Transactional
    @Override
    public boolean createCompany(CompanyDto companyDto) {
        Company company = companyMapper.toEntity(companyDto);
        Company savedCompany = companyRepository.save(company);
        return savedCompany.getId() != null && savedCompany.getId() > 0;
    }

    @Cacheable("companies")
    @Override
    public List<CompanyDto> getAllCompaniesForAdmin() {
        List<Company> companyList = companyRepository.findAll();
        return companyMapper.toDtoList(companyList);
    }

    @Transactional
    @Override
    public void deleteCompanyById(Long id) {
        companyRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean updateCompanyDetails(Long id, CompanyDto companyDto) {
        int updatedRecords = companyRepository.updateCompanyDetails(
                id, companyDto.name(), companyDto.logo(),
                companyDto.industry(), companyDto.size(), companyDto.rating(),
                companyDto.locations(), companyDto.founded(), companyDto.description(),
                companyDto.employees(), companyDto.website()
        );
        return updatedRecords > 0;
    }
}
