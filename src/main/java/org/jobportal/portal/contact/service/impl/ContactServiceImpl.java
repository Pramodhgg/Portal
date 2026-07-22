package org.jobportal.portal.contact.service.impl;


import lombok.RequiredArgsConstructor;
import org.jobportal.portal.constants.ApplicationConstants;
import org.jobportal.portal.contact.service.IcontactService;
import org.jobportal.portal.dto.ContactRequestDto;
import org.jobportal.portal.dto.ContactResponseDto;
import org.jobportal.portal.entity.Contact;
import org.jobportal.portal.mapper.ContactMapper;
import org.jobportal.portal.repository.ContactRepository;
import org.jobportal.portal.util.ApplicationUtility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactServiceImpl implements IcontactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    @Transactional
    public boolean saveContact(ContactRequestDto contactDto) {
        Contact contact = contactMapper.toEntity(contactDto);
        Contact savedContact = contactRepository.save(contact);
        return savedContact.getId() != null;
    }

    @Override
    public List<ContactResponseDto> fetchNewContactMsgs() {
        List<Contact> contacts = contactRepository.findContactByStatusOrderByCreatedAtAsc(ApplicationConstants.NEW_MESSAGE);
        return contactMapper.toDtoList(contacts);
    }

    @Override
    public List<ContactResponseDto> fetchNewContactMsgsWithSort(String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        List<Contact> contacts = contactRepository.findContactsByStatus(ApplicationConstants.NEW_MESSAGE, sort);
        return contactMapper.toDtoList(contacts);
    }

    @Override
    public Page<ContactResponseDto> fetchNewContactMsgsWithPaginationAndSort(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Contact> contactPage = contactRepository.findContactsByStatus(ApplicationConstants.NEW_MESSAGE, pageable);

        return contactPage.map(contactMapper::toDto);
    }

    @Override
    @Transactional
    public Boolean closeContactMsg(Long id, String status) throws IOException {
        int updatedRows = contactRepository.updateStatusById(status, id, ApplicationUtility.getLoggedInUser());
        return updatedRows > 0;
    }

}
