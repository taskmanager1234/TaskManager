package com.company.controllers;

import com.company.model.User;
import com.company.service.TaskJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;


@Controller
public class StartController {
    @Autowired
    TaskJournalService taskJournalService;


    @GetMapping(value = "/home")
    public String get(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        UUID id = ((User) (principal)).getId();
        model.addAttribute("journals",taskJournalService.getJournalsByUserId(id));
        return "home";
    }


}
