package com.demo.contactlist.service;

import com.demo.contactlist.dto.ContactDto;
import com.demo.contactlist.persistence.entity.Contact;
import com.demo.contactlist.persistence.entity.User;
import com.demo.contactlist.persistence.repository.ContactRepository;
import com.demo.contactlist.persistence.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    public ContactService() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public ContactDto saveContact(ContactDto contactDto) {
        if (contactDto.getName() == null || contactDto.getName().isEmpty()) {
            throw new RuntimeException("Name field is required");
        }
        if (contactDto.getPhone() == null || contactDto.getPhone().isEmpty()) {
            throw new RuntimeException("Phone field is required");
        }
        Contact contact = new Contact();
        contact.setName(contactDto.getName());
        contact.setAge(contactDto.getAge());
        contact.setNickname(contactDto.getNickname());
        contact.setPhone(contactDto.getPhone());
        contact.setUserId(this.getUserSession().getId());
        contact = this.contactRepository.save(contact);
        return modelMapper.map(contact, ContactDto.class);
    }

    public ContactDto updateContact(Integer contactId, ContactDto contactDto) {
        Contact contact = this.contactRepository.findByIdAndUserIdAndDeletedFalse(contactId, this.getUserSession().getId());
        if (contact == null) {
            throw new RuntimeException("Record not found");
        }
        if (contactDto.getName() != null) {
            contact.setName(contactDto.getName());
        }
        if (contactDto.getAge() != null) {
            contact.setAge(contactDto.getAge());
        }
        if (contactDto.getNickname() != null) {
            contact.setNickname(contactDto.getNickname());
        }
        if (contactDto.getPhone() != null) {
            contact.setPhone(contactDto.getPhone());
        }
        contact = this.contactRepository.save(contact);
        return modelMapper.map(contact, ContactDto.class);
    }

    public ContactDto deleteContact(Integer contactId) {
        Contact contact = this.contactRepository.findByIdAndUserIdAndDeletedFalse(contactId, this.getUserSession().getId());
        if (contact == null) {
            throw new RuntimeException("Record not found");
        }
        contact.setDeleted(true);
        contact = this.contactRepository.save(contact);
        return modelMapper.map(contact, ContactDto.class);
    }

    public ContactDto getContactById(Integer id) {
        Contact contact = this.contactRepository.findByIdAndUserIdAndDeletedFalse(id, this.getUserSession().getId());
        if (contact == null) {
            throw new RuntimeException("Record not found");
        }
        return modelMapper.map(contact, ContactDto.class);
    }

    public Page<ContactDto> getAllContacts(Pageable pageable) {
        return this.contactRepository.findByDeletedFalseAndUserId(this.getUserSession().getId(), pageable)
                .map(contact -> modelMapper.map(contact, ContactDto.class));
    }

    private User getUserSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return this.userRepository.findByEmailAndDeletedFalse(authentication.getName());
    }
}
