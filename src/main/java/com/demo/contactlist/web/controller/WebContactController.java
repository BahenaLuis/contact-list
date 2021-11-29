package com.demo.contactlist.web.controller;

import com.demo.contactlist.dto.ContactDto;
import com.demo.contactlist.dto.request.CreateContactRequest;
import com.demo.contactlist.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class WebContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(path = "/")
    public String getAll(Model model, Pageable pageable) {

        Page<ContactDto> contactDtoPage = this.contactService.getAllContacts(pageable);
        int totalPages = contactDtoPage.getTotalPages();
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());

        model.addAttribute("contactDtoPage", contactDtoPage);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", pageable.getPageNumber());

        Sort sort = pageable.getSort();
        for (Sort.Order order : sort)
        {
            if (order.getDirection() == Sort.Direction.ASC) {
                model.addAttribute("sortDir_" + order.getProperty(), Sort.Direction.DESC);
            } else {
                model.addAttribute("sortDir_" + order.getProperty(), Sort.Direction.ASC);
            }
        }
        return "contact";
    }

    @RequestMapping(path = "/save/{id}", method = RequestMethod.GET)
    public String showModalSave(Model model, @PathVariable Integer id) {
        if (id != 0) {
            model.addAttribute("contactDto", this.contactService.getContactById(id));
        } else {
            model.addAttribute("contactDto", new ContactDto());
        }
        return "contactForm";
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public String save(Model model, CreateContactRequest createContactRequest) {
        this.contactService.saveContact(createContactRequest);
        return "redirect:/";
    }

    @GetMapping(path = "/delete/{id}") //method = RequestMethod.GET
    public String delete(Model model, @PathVariable Integer id) {
        this.contactService.deleteContact(id);
        return "redirect:/";
    }
}
