package org.jobportal.portal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

public record ContactRequestDto(
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 5, max = 30, message = "Name must be between 5 and 30")
        String name,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email address")
        String email,

        @NotBlank(message = "User type cannot be empty")
        @Pattern(regexp = "Job Seeker|Employer|Other", message = "UserType must be one of: Job seeker, Employer, Other")
        String userType,

        @NotBlank(message = "Subject cannot be empty")
        @Size(min = 5, max = 150, message = "Subject must be between 5 and 150")
        String subject,

        @NotBlank(message = "Message cannot be empty")
        @Size(min = 5, max = 500, message = "Message must be between 5 and 500")
        String message

) implements Serializable {
}