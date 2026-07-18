package org.jobportal.portal.contact.service.impl;


import lombok.RequiredArgsConstructor;
import org.jobportal.portal.contact.service.IcontactService;
import org.jobportal.portal.dto.ContactRequestDto;
import org.jobportal.portal.entity.Contact;
import org.jobportal.portal.repository.ContactRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements IcontactService {

    private final ContactRepository contactRepository;

    @Override
    public boolean saveContact(ContactRequestDto contactDto) {
        boolean result = false;
      Contact contact =  contactRepository.save(transformContactDto(contactDto));
        if (contact.getId() != null) {
            result = true;
        }
        return result;
    }


    private Contact transformContactDto(ContactRequestDto contactRequestDtoDto) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactRequestDtoDto, contact);
        contact.setCreatedAt(Instant.now());
        contact.setStatus("New");
        return contact;
    }

}
