package com.demo.contactlist.service;

import com.demo.contactlist.dto.ContactDto;
import com.demo.contactlist.persistence.entity.Contact;
import com.demo.contactlist.persistence.repository.ContactRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    ModelMapper modelMapper = new ModelMapper();

    public ContactService() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public ContactDto saveContact(ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setId(contactDto.getId());
        contact.setName(contactDto.getName());
        contact.setAge(contactDto.getAge());
        contact.setNickname(contactDto.getNickname());
        contact.setPhone(contactDto.getPhone());
        contact.setUserId(1);
        contact = this.contactRepository.save(contact);
        return modelMapper.map(contact, ContactDto.class);
    }

    public ContactDto deleteContact(Integer contactId) {
        Contact contact = this.contactRepository.getById(contactId);
        if (contact == null) {
            //return 404 not found
        }
        contact.setDeleted(true);
        contact = this.contactRepository.save(contact);
        return modelMapper.map(contact, ContactDto.class);
    }

    public ContactDto getById(Integer id) {
        Contact contact = this.contactRepository.getById(id);
        return modelMapper.map(contact, ContactDto.class);
    }

    public Page<ContactDto> getAllContacts(Pageable pageable) {
        return this.contactRepository.findByDeletedFalse(pageable)
                .map(contact -> modelMapper.map(contact, ContactDto.class));
    }

    public List<ContactDto> getAllContacts() {
        return  this.contactRepository.findByDeletedFalse().stream().map(contact ->
                modelMapper.map(contact, ContactDto.class)).collect(Collectors.toList());
    }
}
