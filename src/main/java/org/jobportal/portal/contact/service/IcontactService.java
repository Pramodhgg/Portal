package org.jobportal.portal.contact.service;

import org.jobportal.portal.dto.ContactRequestDto;
import org.jobportal.portal.dto.ContactResponseDto;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface IcontactService {
    boolean saveContact(ContactRequestDto contactDto);

    List<ContactResponseDto> fetchNewContactMsgs();

    List<ContactResponseDto> fetchNewContactMsgsWithSort(String sortBy, String sortDir);

    Page<ContactResponseDto> fetchNewContactMsgsWithPaginationAndSort(int pageNumber, int pageSize, String sortBy, String sortDir);

    Boolean closeContactMsg(Long id, String status) throws IOException;
}
