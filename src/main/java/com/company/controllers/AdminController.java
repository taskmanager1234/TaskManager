package com.company.controllers;

import com.company.constants.PathTemplates;
import com.company.constants.TaskManagerConstants;
import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class AdminController {

    private static class ATTRIBUTE_NAME {
        public static final String ALL_USERS = "allUsers";
    }

    private static class PathVariables {
        public static final String USER_ID = "userId";
    }

    @Autowired
    private UserService userService;

    @GetMapping(TaskManagerConstants.ADMIN_URL)
    public String getUsers(Model model) {
        model.addAttribute(ATTRIBUTE_NAME.ALL_USERS, userService.allUsers());
        return PathTemplates.ADMIN;
    }


    @PostMapping(TaskManagerConstants.USER_URL)
    public String deleteUser(@PathVariable(name = PathVariables.USER_ID) UUID userId) {
        userService.deleteUser(userId);
        return PathTemplates.REDIRECT_TO_ADMIN;
    }

}