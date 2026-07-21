package org.jobportal.portal.mapper;

import lombok.RequiredArgsConstructor;
import org.jobportal.portal.dto.JobDto;
import org.jobportal.portal.entity.Company;
import org.jobportal.portal.entity.Job;
import org.jobportal.portal.repository.CompanyRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JobMapper {
    private final CompanyRepository companyRepository;

    public JobDto toDto(Job job) {
        if (job == null) {
            return null;
        }
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

    public List<JobDto> toDtoList(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            return List.of();
        }
        return jobs.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Job toEntity(JobDto jobDto) {
        if (jobDto == null) {
            return null;
        }
        Job job = new Job();
        job.setId(jobDto.id());
        job.setTitle(jobDto.title());
        job.setLocation(jobDto.location());
        job.setWorkType(jobDto.workType());
        job.setJobType(jobDto.jobType());
        job.setCategory(jobDto.category());
        job.setExperienceLevel(jobDto.experienceLevel());
        job.setSalaryMin(jobDto.salaryMin());
        job.setSalaryMax(jobDto.salaryMax());
        job.setSalaryCurrency(jobDto.salaryCurrency());
        job.setSalaryPeriod(jobDto.salaryPeriod());
        job.setDescription(jobDto.description());
        job.setRequirements(jobDto.requirements());
        job.setBenefits(jobDto.benefits());
        job.setPostedDate(jobDto.postedDate());
        job.setApplicationDeadline(jobDto.applicationDeadline());
        job.setApplicationsCount(jobDto.applicationsCount());
        job.setFeatured(jobDto.featured());
        job.setUrgent(jobDto.urgent());
        job.setRemote(jobDto.remote());
        job.setStatus(jobDto.status());

        Company company = companyRepository.findById(jobDto.companyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + jobDto.companyId()));
        job.setCompany(company);

        return job;
    }
}
