package org.jobportal.portal.contact.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jobportal.portal.contact.service.IcontactService;
import org.jobportal.portal.dto.ContactRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final IcontactService contactService;

    @PostMapping(path = "public", version = "1.0")
    public ResponseEntity<String> contact(@RequestBody @Valid ContactRequestDto contactDto) {
       boolean isSaved = contactService.saveContact(contactDto);
       if(isSaved){
           return ResponseEntity.status(HttpStatus.CREATED).body("success");
       }else{
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
       }
    }
}
