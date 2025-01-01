package com.scm.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.Message.MessageBuilder;
import com.scm.helper.MessageType;
import com.scm.models.Providers;
import com.scm.models.User;
import com.scm.services.UserService;
import com.scm.utils.Utility;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegistration(@Valid @ModelAttribute("userForm") UserForm userForm,
             HttpSession session, BindingResult result) {
                boolean isRegistrationSuccessful = true;
        if (result.hasErrors()) {
            List<Map<String, String>> errors = result.getFieldErrors().stream()
                    .map(error -> Map.of("field", error.getField(), "message", error.getDefaultMessage())).toList();
                    isRegistrationSuccessful = false;
        }else{
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setEmail(userForm.getEmail());
        user.setPassword(Utility.encryptPassword(userForm.getPassword()));
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setName(userForm.getName());
        user.setProvider(Providers.SELF);
        user.setProfilePic(
                "https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg?t=st=1735635207~exp=1735638807~hmac=7aa428a8e55c595c2c5a72b9e61421172005ebe541fe70630b3bdb331e819931&w=740");
        User userRegistered = userService.registerUser(user);
        
        if (userRegistered == null) {
            isRegistrationSuccessful = false;
        }
        }
        if (isRegistrationSuccessful) {
           // redirectAttributes.addFlashAttribute("message", "Registration successful!");
           Message message =  Message.builder().content("Registration successful!").type(MessageType.green).build();
           session.setAttribute("message", message);
            return "redirect:/users/success_page";
        } else {
           // redirectAttributes.addFlashAttribute("message", "Registration failed. user already exists !.");
           Message message =  Message.builder().content("Registration failed. user already exists !").type(MessageType.red).build();
           session.setAttribute("message", message);
            return "redirect:/users/register";
        }
    
    }

    @GetMapping("/success_page")
    public String successPage() {
        return "success_page";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @PostMapping("/authenticate")
    public String postMethodName(@ModelAttribute("userForm") UserForm userForm,
            RedirectAttributes redirectAttributes) {

        return "services";
    }

}
