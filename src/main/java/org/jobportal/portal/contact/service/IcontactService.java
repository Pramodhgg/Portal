package org.jobportal.portal.contact.service;

import org.jobportal.portal.dto.ContactRequestDto;

public interface IcontactService {
    boolean saveContact(ContactRequestDto contactDto);
}
