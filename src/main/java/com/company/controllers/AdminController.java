package com.company.controllers;

import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//@RequestMapping(path = "/admin")
@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    //todo привести имена эндпоинтов, имена страниц и имена методов в соответствие. Устранить ллитералы в коде. Удалить не используемые и закомменченные блоки кода.
    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin/{userId}")
    public String  deleteUser(@PathVariable(name = "userId") UUID userId) {
            userService.deleteUser(userId);
        return "redirect:/admin";
    }

//
//    @GetMapping("/admin/gt/{userId}")
//    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
//        model.addAttribute("allUsers", userService.usergtList(userId));
//        return "admin";
//    }
}