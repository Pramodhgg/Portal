package org.jobportal.portal.contact.service.impl;


import lombok.RequiredArgsConstructor;
import org.jobportal.portal.constants.ApplicationConstants;
import org.jobportal.portal.contact.service.IcontactService;
import org.jobportal.portal.dto.ContactRequestDto;
import org.jobportal.portal.dto.ContactResponseDto;
import org.jobportal.portal.entity.Contact;
import org.jobportal.portal.repository.ContactRepository;
import org.jobportal.portal.util.ApplicationUtility;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactServiceImpl implements IcontactService {

    private final ContactRepository contactRepository;

    @Override
    @Transactional
    public boolean saveContact(ContactRequestDto contactDto) {
        boolean result = false;
      Contact contact =  contactRepository.save(transformToEntity(contactDto));
        if (contact.getId() != null) {
            result = true;
        }
        return result;
    }

    @Override
    public List<ContactResponseDto> fetchNewContactMsgs() {
       List<Contact> contacts = contactRepository.findContactByStatusOrderByCreatedAtAsc(ApplicationConstants.NEW_MESSAGE);
       List<ContactResponseDto> contactResponseDtos = contacts.stream().map(this::transformToDto).toList();
       return contactResponseDtos;
    }

    @Override
    public List<ContactResponseDto> fetchNewContactMsgsWithSort(String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        List<Contact> contacts = contactRepository.findContactsByStatus(ApplicationConstants.NEW_MESSAGE, sort);
        List<ContactResponseDto> contactResponseDtos = contacts.stream().map(this::transformToDto).toList();

        return contactResponseDtos;
    }

    @Override
    public Page<ContactResponseDto> fetchNewContactMsgsWithPaginationAndSort(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Contact> contactPage = contactRepository.findContactsByStatus(ApplicationConstants.NEW_MESSAGE, pageable);

        Page<ContactResponseDto> contactResponseDtos = contactPage.map(this::transformToDto);
        return contactResponseDtos;
    }

    @Override
    @Transactional
    public Boolean closeContactMsg(Long id, String status) throws IOException {
        int updatedRows = contactRepository.updateStatusById(status, id, ApplicationUtility.getLoggedInUser());
        return updatedRows > 0;
    }


    private Contact transformToEntity(ContactRequestDto contactRequestDtoDto) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactRequestDtoDto, contact);
        contact.setCreatedAt(Instant.now());
        contact.setStatus("New");
        return contact;
    }

    private ContactResponseDto transformToDto(Contact contact) {
        ContactResponseDto contactResponseDto = new ContactResponseDto(contact.getId(),
                contact.getName(), contact.getEmail(), contact.getUserType(), contact.getSubject(),
                contact.getMessage(), contact.getStatus(), contact.getCreatedAt());
        return contactResponseDto;
    }

}
