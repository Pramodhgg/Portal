package org.jobportal.portal.mapper;

import lombok.RequiredArgsConstructor;
import org.jobportal.portal.dto.ProfileDto;
import org.jobportal.portal.entity.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

    public ProfileDto toDto(Profile profile) {
        if (profile == null) {
            return null;
        }
        return new ProfileDto(
                profile.getId(),
                profile.getUser() != null ? profile.getUser().getId() : null,
                profile.getHeadline(),
                profile.getSummary(),
                profile.getSkills(),
                profile.getExperience(),
                profile.getEducation(),
                profile.getProfilePictureUrl(),
                profile.getPhoneNumber(),
                profile.getLocation(),
                profile.getWebsite(),
                profile.getLinkedinUrl(),
                profile.getGithubUrl()
        );
    }

    public List<ProfileDto> toDtoList(List<Profile> profiles) {
        if (profiles == null || profiles.isEmpty()) {
            return List.of();
        }
        return profiles.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Profile toEntity(ProfileDto profileDto) {
        if (profileDto == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(profileDto.id());
        profile.setHeadline(profileDto.headline());
        profile.setSummary(profileDto.summary());
        profile.setSkills(profileDto.skills());
        profile.setExperience(profileDto.experience());
        profile.setEducation(profileDto.education());
        profile.setProfilePictureUrl(profileDto.profilePictureUrl());
        profile.setPhoneNumber(profileDto.phoneNumber());
        profile.setLocation(profileDto.location());
        profile.setWebsite(profileDto.website());
        profile.setLinkedinUrl(profileDto.linkedinUrl());
        profile.setGithubUrl(profileDto.githubUrl());

        return profile;
    }
}
