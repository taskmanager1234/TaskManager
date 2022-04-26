package com.company.controllers;

import com.company.constants.PathTemplates;
import com.company.constants.TaskManagerConstants;
import com.company.model.User;
import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class RegistrationController {


    private static class ATTRIBUTE_NAME {
        public static final String USER_DATA = "userData";
        public static final String PASSWORD_ERROR = "passwordError";
        public static final String USERNAME_ERROR = "usernameError";
    }


    @Autowired
    private UserService userService;

    @GetMapping(TaskManagerConstants.REGISTRATION_URL)
    public String registration(Model model) {
        model.addAttribute(ATTRIBUTE_NAME.USER_DATA, new User());

        return PathTemplates.REGISTRATION;
    }

    @PostMapping(TaskManagerConstants.REGISTRATION_URL)
    public String registrationUser(@ModelAttribute(ATTRIBUTE_NAME.USER_DATA) User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return PathTemplates.REGISTRATION;
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute(ATTRIBUTE_NAME.PASSWORD_ERROR, "Пароли не совпадают");
            return PathTemplates.REGISTRATION;
        }
        user.setId(UUID.randomUUID());
        if (!userService.saveUser(user)) {
            model.addAttribute(ATTRIBUTE_NAME.USERNAME_ERROR, "Пользователь с таким именем уже существует");
            return PathTemplates.REGISTRATION;
        }

        return PathTemplates.REDIRECT_LOGIN;
    }
}
