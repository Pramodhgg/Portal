package org.jobportal.portal.company.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobportal.portal.constants.ApplicationConstants;
import org.jobportal.portal.dto.CompanyDto;
import org.jobportal.portal.dto.JobDto;
import org.jobportal.portal.entity.Company;
import org.jobportal.portal.entity.Job;
import org.jobportal.portal.repository.CompanyRepository;
import org.jobportal.portal.company.service.ICompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements ICompanyService {
    private final CompanyRepository companyRepository;


    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companyList =  companyRepository.findAllWithJobsByStatus(ApplicationConstants.ACTIVE_STATUS);
        return companyList.stream().map(this::trasnformCompanyTodo).collect(Collectors.toList());
    }



    private CompanyDto trasnformCompanyTodo(Company company) {
        List<JobDto> jobDtos = company.getJobs().stream().map(this::transformJobToDto).collect(Collectors.toList());
        return new CompanyDto(company.getId(), company.getName(),company.getLogo(), company.getDescription(),
                company.getIndustry(), company.getRating(), company.getSize(), company.getFounded(), company.getLocations(),
                company.getEmployees(), company.getWebsite(), company.getCreatedAt(), jobDtos);
    }

    private JobDto transformJobToDto(Job job) {
        return new JobDto(
                job.getId(),
                job.getTitle(),
                job.getCompany().getId(),
                job.getCompany().getName(),
                job.getCompany().getLogo(),
                job.getLocation(),
                job.getWorkType(),
                job.getJobType(),
                job.getCategory(),
                job.getExperienceLevel(),
                job.getSalaryMin(),
                job.getSalaryMax(),
                job.getSalaryCurrency(),
                job.getSalaryPeriod(),
                job.getDescription(),
                job.getRequirements(),
                job.getBenefits(),
                job.getPostedDate(),
                job.getApplicationDeadline(),
                job.getApplicationsCount(),
                job.getFeatured(),
                job.getUrgent(),
                job.getRemote(),
                job.getStatus()
        );
    }
}
