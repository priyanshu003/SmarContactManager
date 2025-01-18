package com.scm.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scm.forms.UserForm;
import com.scm.helper.AppConstants;
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
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/page")
public class PageController {

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }

    @GetMapping("/")
    public String indexPage() {
        return "redirect:/home";
    }
     // services

     @RequestMapping("/services")
     public String servicesPage() {
         System.out.println("services page loading");
         return "services";
     }
 

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @PostMapping("/do-register")
    public String processRegistration(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult result,
            HttpSession session) {
        boolean isRegistrationSuccessful = true;

        logger.info("entring user function register");
        if (result.hasErrors()) {
            // List<Map<String, String>> errors = result.getFieldErrors().stream()
            // .map(error -> Map.of("field", error.getField(), "message",
            // error.getDefaultMessage())).toList();
            // isRegistrationSuccessful = false;

            logger.info("error coming loading rigister page with errors");
            return "register";
        }
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setEmail(userForm.getEmail());
        user.setPassword(Utility.encryptPassword(userForm.getPassword()));
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setName(userForm.getName());
        user.setProvider(Providers.SELF);
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEnabled(true);
        user.setAbout(userForm.getAbout());
        user.setProfilePic(
                "https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg?t=st=1735635207~exp=1735638807~hmac=7aa428a8e55c595c2c5a72b9e61421172005ebe541fe70630b3bdb331e819931&w=740");
        User userRegistered = userService.registerUser(user);

        if (userRegistered == null) {
            isRegistrationSuccessful = false;
        }

        if (isRegistrationSuccessful) {
            // redirectAttributes.addFlashAttribute("message", "Registration successful!");
            Message message = Message.builder().content("Registration successful!").type(MessageType.green).build();
            session.setAttribute("message", message);
            return "redirect:/page/success_page";
        } else {
            // redirectAttributes.addFlashAttribute("message", "Registration failed. user
            // already exists !.");
            Message message = Message.builder().content("Registration failed. user already exists !")
                    .type(MessageType.red).build();
            session.setAttribute("message", message);
            return "redirect:/page/register";
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
            RedirectAttributes redirectAttributes , HttpSession session) {
                System.out.println("Entered /authenticate endpoint with userForm: " + userForm);
                Optional<User> usr =userService.findUserByEmail(userForm.getEmail());
                boolean isRegistrationSuccessful = false;
                if(usr != null){
                    isRegistrationSuccessful = true;
                }   
                if (isRegistrationSuccessful) {
                    // redirectAttributes.addFlashAttribute("message", "Registration successful!");
                    Message message = Message.builder().content("Registration successful!").type(MessageType.green).build();
                    session.setAttribute("message", message);
                    return "redirect:/page/services";
                } else {
                    // redirectAttributes.addFlashAttribute("message", "Registration failed. user
                    // already exists !.");
                    Message message = Message.builder().content("Registration failed. user already exists !")
                            .type(MessageType.red).build();
                    session.setAttribute("message", message);
                    return "redirect:/page/login";
                }
    }

}
