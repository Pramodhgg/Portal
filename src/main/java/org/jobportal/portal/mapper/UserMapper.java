package org.jobportal.portal.mapper;

import lombok.RequiredArgsConstructor;
import org.jobportal.portal.dto.UserDto;
import org.jobportal.portal.entity.JobPortalUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserDto toDto(JobPortalUser user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setMobileNumber(user.getMobileNumber());
        userDto.setRole(user.getRole() != null ? user.getRole().getName() : null);
        userDto.setCompanyId(user.getCompany() != null ? user.getCompany().getId() : null);

        return userDto;
    }

    public List<UserDto> toDtoList(List<JobPortalUser> users) {
        if (users == null || users.isEmpty()) {
            return List.of();
        }
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public JobPortalUser toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        JobPortalUser user = new JobPortalUser();
        user.setId(userDto.getUserId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setMobileNumber(userDto.getMobileNumber());

        return user;
    }
}
