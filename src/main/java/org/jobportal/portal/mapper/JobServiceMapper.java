package org.jobportal.portal.mapper;

import lombok.RequiredArgsConstructor;
import org.jobportal.portal.dto.JobDto;
import org.jobportal.portal.entity.Job;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobServiceMapper {

    public Job tranformDtoToEntity(JobDto jobDto) {
        Job job = new Job();
        BeanUtils.copyProperties(jobDto, job);
        return job;
    }
}
