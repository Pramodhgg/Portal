package org.jobportal.portal.mapper;

import lombok.RequiredArgsConstructor;
import org.jobportal.portal.dto.ApplyJobRequestDto;
import org.jobportal.portal.dto.JobApplicationDto;
import org.jobportal.portal.entity.Job;
import org.jobportal.portal.entity.JobApplication;
import org.jobportal.portal.entity.JobPortalUser;
import org.jobportal.portal.repository.JobRepository;
import org.jobportal.portal.repository.JobPortalUserRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JobApplicationMapper {
    private final JobRepository jobRepository;
    private final JobPortalUserRepository userRepository;
    private final JobMapper jobMapper;
    private final UserMapper userMapper;

    public JobApplicationDto toDto(JobApplication jobApplication) {
        if (jobApplication == null) {
            return null;
        }
        return new JobApplicationDto(
                jobApplication.getId(),
                jobApplication.getUser() != null ? userMapper.toDto(jobApplication.getUser()) : null,
                jobApplication.getJob() != null ? jobMapper.toDto(jobApplication.getJob()) : null,
                jobApplication.getCoverLetter(),
                jobApplication.getApplicationDate(),
                jobApplication.getStatus()
        );
    }

    public List<JobApplicationDto> toDtoList(List<JobApplication> jobApplications) {
        if (jobApplications == null || jobApplications.isEmpty()) {
            return List.of();
        }
        return jobApplications.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public JobApplication toEntity(ApplyJobRequestDto applyJobRequestDto, Long userId, Long jobId) {
        if (applyJobRequestDto == null) {
            return null;
        }
        JobApplication jobApplication = new JobApplication();

        JobPortalUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found with id: " + jobId));

        jobApplication.setUser(user);
        jobApplication.setJob(job);
        jobApplication.setCoverLetter(applyJobRequestDto.coverLetter());
        jobApplication.setApplicationDate(Instant.now());
        jobApplication.setStatus("APPLIED");

        return jobApplication;
    }
}
