package com.company.controllers;

import com.company.constants.PathTemplates;
import com.company.constants.TaskManagerConstants;
import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping(TaskManagerConstants.ADMIN_URL)//TODO rename url
    public String getUsers(Model model) {
        model.addAttribute(ATTRIBUTE_NAME.ALL_USERS, userService.allUsers());
        return PathTemplates.ADMIN;
    }


//    @PostMapping(TaskManagerConstants.USER_URL)
//    public String deleteUser(@PathVariable(name = PathVariables.USER_ID) UUID userId) {
//
//
//        userService.deleteUser(userId);
//        return PathTemplates.REDIRECT_TO_ADMIN;
//    }

    @PostMapping("/admin")
    public String deleteUser(@RequestParam("user_checkbox") String[] userIds){
                for(String currentId: userIds){
                    userService.deleteUser(UUID.fromString(currentId));
                }
        return PathTemplates.REDIRECT_TO_ADMIN;
    }

}