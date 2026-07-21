package org.jobportal.portal.mapper;

import lombok.RequiredArgsConstructor;
import org.jobportal.portal.dto.ContactRequestDto;
import org.jobportal.portal.dto.ContactResponseDto;
import org.jobportal.portal.entity.Contact;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContactMapper {

    public ContactResponseDto toDto(Contact contact) {
        if (contact == null) {
            return null;
        }
        return new ContactResponseDto(
                contact.getId(),
                contact.getName(),
                contact.getEmail(),
                contact.getSubject(),
                contact.getMessage(),
                contact.getUserType(),
                contact.getStatus(),
                contact.getCreatedAt()
        );
    }

    public List<ContactResponseDto> toDtoList(List<Contact> contacts) {
        if (contacts == null || contacts.isEmpty()) {
            return List.of();
        }
        return contacts.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Contact toEntity(ContactRequestDto contactRequestDto) {
        if (contactRequestDto == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setName(contactRequestDto.name());
        contact.setEmail(contactRequestDto.email());
        contact.setSubject(contactRequestDto.subject());
        contact.setMessage(contactRequestDto.message());
        contact.setUserType(contactRequestDto.userType());
        contact.setStatus("NEW");
        contact.setCreatedAt(Instant.now());

        return contact;
    }
}
