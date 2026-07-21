package org.jobportal.portal.mapper;

import lombok.RequiredArgsConstructor;
import org.jobportal.portal.dto.CompanyDto;
import org.jobportal.portal.dto.JobDto;
import org.jobportal.portal.entity.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyMapper {
    private final JobMapper jobMapper;

    public CompanyDto toDto(Company company) {
        if (company == null) {
            return null;
        }
        List<JobDto> jobDtos = company.getJobs() != null
                ? company.getJobs().stream()
                .map(jobMapper::toDto)
                .collect(Collectors.toList())
                : List.of();

        return new CompanyDto(
                company.getId(),
                company.getName(),
                company.getLogo(),
                company.getIndustry(),
                company.getSize(),
                company.getRating(),
                company.getLocations(),
                company.getFounded(),
                company.getDescription(),
                company.getEmployees(),
                company.getWebsite(),
                company.getCreatedAt(),
                jobDtos
        );
    }

    public List<CompanyDto> toDtoList(List<Company> companies) {
        if (companies == null || companies.isEmpty()) {
            return List.of();
        }
        return companies.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Company toEntity(CompanyDto companyDto) {
        if (companyDto == null) {
            return null;
        }
        Company company = new Company();
        company.setId(companyDto.id());
        company.setName(companyDto.name());
        company.setLogo(companyDto.logo());
        company.setIndustry(companyDto.industry());
        company.setSize(companyDto.size());
        company.setRating(companyDto.rating());
        company.setLocations(companyDto.locations());
        company.setFounded(companyDto.founded());
        company.setDescription(companyDto.description());
        company.setEmployees(companyDto.employees());
        company.setWebsite(companyDto.website());

        return company;
    }
}
