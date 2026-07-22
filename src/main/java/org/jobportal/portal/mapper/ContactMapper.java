package org.jobportal.portal.mapper;

import lombok.RequiredArgsConstructor;
import org.jobportal.portal.dto.ContactRequestDto;
import org.jobportal.portal.dto.ContactResponseDto;
import org.jobportal.portal.entity.Contact;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ContactMapper {

    public Contact transformToEntity(ContactRequestDto contactRequestDtoDto) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactRequestDtoDto, contact);
        contact.setCreatedAt(Instant.now());
        contact.setStatus("New");
        return contact;
    }

    public ContactResponseDto transformToDto(Contact contact) {
        ContactResponseDto contactResponseDto = new ContactResponseDto(contact.getId(),
                contact.getName(), contact.getEmail(), contact.getUserType(), contact.getSubject(),
                contact.getMessage(), contact.getStatus(), contact.getCreatedAt());
        return contactResponseDto;
    }
}
