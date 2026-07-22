package org.jobportal.portal.mapper;

import lombok.RequiredArgsConstructor;
import org.jobportal.portal.dto.CompanyDto;
import org.jobportal.portal.dto.JobDto;
import org.jobportal.portal.entity.Company;
import org.jobportal.portal.entity.Job;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyMapper {
    private final JobMapper jobMapper;

    public CompanyDto transformCompanyTodo(Company company) {
        List<JobDto> jobDtos = company.getJobs().stream().map(jobMapper::transformJobToDto).collect(Collectors.toList());
        return new CompanyDto(company.getId(), company.getName(), company.getLogo(), company.getDescription(),
                company.getIndustry(), company.getRating(), company.getSize(), company.getFounded(), company.getLocations(),
                company.getEmployees(), company.getWebsite(), company.getCreatedAt(), jobDtos);
    }

    public CompanyDto transformCompanyToDtoForAdmin(Company company) {
        return new CompanyDto(company.getId(), company.getName(), company.getLogo(),
                company.getIndustry(), company.getSize(), company.getRating(),
                company.getLocations(), company.getFounded(), company.getDescription(),
                company.getEmployees(), company.getWebsite(), company.getCreatedAt(), null);
    }
}
