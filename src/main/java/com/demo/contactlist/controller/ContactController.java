package com.demo.contactlist.controller;

import com.demo.contactlist.dto.ContactDto;
import com.demo.contactlist.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(path = "/contact", method = RequestMethod.GET)
    public Page<ContactDto> getAll(Pageable pageable) {
        Page<ContactDto> contactDtoPage = this.contactService.getAllContacts(pageable);
        return contactDtoPage;
    }

    @RequestMapping(path = "/contact/{id}", method = RequestMethod.GET)
    public ContactDto getById(@PathVariable Integer id) {
        return this.contactService.getContactById(id);
    }

    @RequestMapping(path = "/contact", method = RequestMethod.POST)
    public ContactDto save(ContactDto contactDto) {
        return this.contactService.saveContact(contactDto);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.PUT)
    public ContactDto update(ContactDto contactDto, @PathVariable Integer id) {
        return this.contactService.saveContact(contactDto);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE)
    public ContactDto delete(@PathVariable Integer id) {
        return this.contactService.deleteContact(id);
    }
}
