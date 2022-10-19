package com.nikitin.codeinsideproject.controller;

import com.nikitin.codeinsideproject.dto.PersonDto;
import com.nikitin.codeinsideproject.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class AuthenticationController {

    private final PersonService personService;

    @Autowired
    public AuthenticationController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/login")
    public String getPageLogin() {
        return "auth/login";
    }

    @GetMapping("/sign_up")
    public String getPageSignUp(Model model,
                                @RequestParam(required = false) Boolean success,
                                @RequestParam(required = false) Boolean error) {
        model.addAttribute("person" , new PersonDto());
        return "auth/sign_up";
    }

    @PostMapping("/sign_up/registration")
    public String signUpPerson(@ModelAttribute("person") @Valid PersonDto personDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "auth/sign_up";

        if (personService.emailExist(personDto.getEmail())) {
            personService.savePerson(personDto);
            return "redirect:/sign_up?success=true";
        }
        return "redirect:/sign_up?error=true";
    }
}
