package com.scm.controllers;

import java.security.Principal;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.helper.Helper;
import com.scm.models.User;
import com.scm.services.UserService;




@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = Logger.getLogger(UserController.class.getName());
    @Autowired
    UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            Optional<User> loggedInUser = userService.findUserByEmail(username);
            model.addAttribute("loggedInUser", loggedInUser);
        }
        return "user/dashboard";
    }

     @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {   
        return "user/profile";
    }
    

}
