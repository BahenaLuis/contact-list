package com.demo.contactlist.service;

import com.demo.contactlist.dto.ContactDto;
import com.demo.contactlist.dto.request.CreateContactRequest;
import com.demo.contactlist.dto.request.UpdateContactRequest;
import com.demo.contactlist.exception.ResourceNotFoundException;
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

    public ContactDto saveContact(CreateContactRequest createContactRequest) {
        if (createContactRequest.getName() == null || createContactRequest.getName().isEmpty()) {
            throw new RuntimeException("Name field is required");
        }
        if (createContactRequest.getPhone() == null || createContactRequest.getPhone().isEmpty()) {
            throw new RuntimeException("Phone field is required");
        }
        Contact contact = new Contact();
        contact.setName(createContactRequest.getName());
        contact.setAge(createContactRequest.getAge());
        contact.setNickname(createContactRequest.getNickname());
        contact.setPhone(createContactRequest.getPhone());
        contact.setUserId(this.getUserSession().getId());
        contact = this.contactRepository.save(contact);
        return modelMapper.map(contact, ContactDto.class);
    }

    public ContactDto updateContact(Integer contactId, UpdateContactRequest updateContactRequest) {
        Contact contact = this.contactRepository.findByIdAndUserIdAndDeletedFalse(contactId, this.getUserSession().getId());
        if (contact == null) {
            throw new ResourceNotFoundException("Record not found");
        }
        if (updateContactRequest.getName() != null) {
            contact.setName(updateContactRequest.getName());
        }
        if (updateContactRequest.getAge() != null) {
            contact.setAge(updateContactRequest.getAge());
        }
        if (updateContactRequest.getNickname() != null) {
            contact.setNickname(updateContactRequest.getNickname());
        }
        if (updateContactRequest.getPhone() != null) {
            contact.setPhone(updateContactRequest.getPhone());
        }
        contact = this.contactRepository.save(contact);
        return modelMapper.map(contact, ContactDto.class);
    }

    public ContactDto deleteContact(Integer contactId) {
        Contact contact = this.contactRepository.findByIdAndUserIdAndDeletedFalse(contactId, this.getUserSession().getId());
        if (contact == null) {
            throw new ResourceNotFoundException("Record not found");
        }
        contact.setDeleted(true);
        contact = this.contactRepository.save(contact);
        return modelMapper.map(contact, ContactDto.class);
    }

    public ContactDto getContactById(Integer id) {
        Contact contact = this.contactRepository.findByIdAndUserIdAndDeletedFalse(id, this.getUserSession().getId());
        if (contact == null) {
            throw new ResourceNotFoundException("Record not found");
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
